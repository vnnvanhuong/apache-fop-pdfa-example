package vn.nvanhuong.apache.fop.pdfa.example.test;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import vn.nvanhuong.apache.fop.pdfa.example.FopPdfaGenerator;

public class FopPdfaGeneratorTest {
	
	@Test
	public void should_generate_pdf_a() throws SAXException, IOException {
		FopPdfaGenerator.convertXslFoToPdf();
	}
}
