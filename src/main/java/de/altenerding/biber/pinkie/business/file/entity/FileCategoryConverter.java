package de.altenerding.biber.pinkie.business.file.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FileCategoryConverter implements AttributeConverter<FileCategory, String> {
	@Override
	public String convertToDatabaseColumn(FileCategory directory) {
		return directory.getDirectoryPath();
	}

	@Override
	public FileCategory convertToEntityAttribute(String directoryPath) {
		return FileCategory.get(directoryPath);
	}
}
