package de.altenerding.biber.pinkie.presentation.webserver;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	@Inject
	private Logger logger;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("incoming post with uploading data");
		processRequest(req, resp);
	}

	protected void processRequest(HttpServletRequest request,
								  HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// Create path components to save the file
		String folder = request.getParameter("folder");

		String resourceFolder = System.getProperty("resourceFolder");

		if (resourceFolder == null || resourceFolder.isEmpty()) {
			throw new ServletException("Please provide the VM Option \'-DresourceFolder=\'");
		}

		folder = resourceFolder + File.separator + folder;

		final Part filePart = request.getPart("file");
		String fileName = getFileName(filePart);

		final PrintWriter writer = response.getWriter();

		try (InputStream  filecontent = filePart.getInputStream()) {
			Files.copy(filecontent, Paths.get(folder + File.separator
					+ fileName), StandardCopyOption.REPLACE_EXISTING);
			writer.println("New file " + fileName + " created at " + folder);
			logger.info("File '{}' being uploaded to {}", fileName, folder);
		} catch (Exception e) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent "
					+ "location.");
			writer.println("<br/> ERROR: " + e.getMessage());

			logger.error("Problems during file upload. ", e);
		}
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
