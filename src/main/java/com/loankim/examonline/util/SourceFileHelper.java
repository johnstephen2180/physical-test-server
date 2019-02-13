package com.loankim.examonline.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author LamHM
 *
 */
public class SourceFileHelper {
	public static final String EXPORT_FOLDER = "export/";
	public static final String RESOURCE_FOLDER = "resources/";
	private static final XMLInputFactory f = XMLInputFactory.newFactory();


	public static XMLStreamReader getStreamReader(String fileName) throws FileNotFoundException, XMLStreamException {
		return f.createXMLStreamReader(new FileInputStream(RESOURCE_FOLDER + fileName), "UTF-8");
	}


	public static String exportJsonFile(Object data, String fileName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File(EXPORT_FOLDER + fileName), data);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
	}


	public static String updateFile(Object data, String fileName) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File(RESOURCE_FOLDER + fileName), data);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
	}


	public static String saveFile(MultipartFile file, String fileName) throws IOException {
		InputStream inputStream = file.getInputStream();
		ExportPPTxToImage.convertPPTxToImage(inputStream);
		// byte[] bytes = file.getBytes();
		// BufferedOutputStream stream = new BufferedOutputStream(
		// new FileOutputStream(new File(RESOURCE_FOLDER + fileName)));
		// stream.write(bytes);
		// stream.close();
		// return new String(bytes);
		return "ok";
	}
}
