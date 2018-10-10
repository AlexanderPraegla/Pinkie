package de.altenerding.biber.pinkie.business.nuLiga.control;

import de.altenerding.biber.pinkie.business.nuLiga.entity.StandingEntry;
import de.altenerding.biber.pinkie.business.nuLiga.entity.TeamScheduleEntry;
import de.altenerding.biber.pinkie.business.team.entity.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NuLigaDataProcessorTest {

    private static final String NULIGA_SCHEDULE_H1_FILE = "nuLiga_gamesSchedule_complete_h1.html";
    private static final String NULIGA_SCHEDULE_MC2_FILE = "nuLiga_gamesSchedule_complete_mC2.html";
    private static final String NULIGA_RANKING_FILE_H1 = "nulLiga_ranking_table_h1.html";
    private static final String NULIGA_RANKING_FILE_MC2 = "nulLiga_ranking_table_mC2.html";

    private static Stream<Arguments> teamData() {
        return Stream.of(
                Arguments.of("H1", 26, NULIGA_SCHEDULE_H1_FILE),
                Arguments.of("mC2", 16, NULIGA_SCHEDULE_MC2_FILE)
        );
    }

    private static Stream<Arguments> teamRankingData() {
        return Stream.of(
                Arguments.of("H1", 14, NULIGA_SCHEDULE_H1_FILE),
                Arguments.of("mC2", 10, NULIGA_RANKING_FILE_MC2)
        );
    }

    @DisplayName("Test Ranking parsing")
    @ParameterizedTest(name = "{0} should have {1} teams in ranking table")
    @MethodSource("teamRankingData")
    void shouldParseRankingDocument(String teamName, int numberOfTeams, String fileName) throws IOException {
        System.out.println("Testing parsing of ranking");
        NuLigaDataProcessor nuLigaDataProcessor = new NuLigaDataProcessor();

        InputStream ranking = getClass().getClassLoader().getResourceAsStream(NULIGA_RANKING_FILE_H1);
        Document rankingDocument = Jsoup.parse(ranking, "UTF-8", "");
        Team team = new Team();
        List<StandingEntry> rankings = nuLigaDataProcessor.parseRankingHtml(team, rankingDocument);

        assertThat(rankings).hasSize(14);
        assertThat(team.getUrlTeamSchedule()).isEqualToIgnoringCase("https://bhv-handball.liga.nu/cgi-bin/WebObjects/nuLigaHBDE.woa/wa/teamPortrait?teamtable=1565717&pageState=gesamt&championship=BHV+2018%2F19&group=227457");
    }

    @DisplayName("Test Schedule parsing")
    @ParameterizedTest(name = "{0} should have {1} matches")
    @MethodSource("teamData")
    void shouldParseCompleteSchedule(String teamName, int numberOfMatches, String fileName) throws IOException {
        NuLigaDataProcessor nuLigaDataProcessor = new NuLigaDataProcessor();

        InputStream schedule = getClass().getClassLoader().getResourceAsStream(fileName);
        Document scheduleDokument = Jsoup.parse(schedule, "UTF-8", "");
        List<TeamScheduleEntry> teamScheduleEntries = nuLigaDataProcessor.parseScheduleHtml(null, scheduleDokument);

        assertThat(teamScheduleEntries).hasSize(numberOfMatches);
    }
}