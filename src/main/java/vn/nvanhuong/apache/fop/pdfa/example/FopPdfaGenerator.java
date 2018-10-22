package vn.nvanhuong.apache.fop.pdfa.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

public class FopPdfaGenerator {
	private static final String FOP_FO_FILE = "helloworld.fo";
	private static final String CONFIG_PROPERTIES = "config.properties";
	private static final String FOP_XCONF = "fop.xconf";
	private static final Logger LOGGER = Logger.getLogger(FopPdfaGenerator.class.getName());

	@SuppressWarnings("unchecked")
	public static void convertXslFoToPdf() throws SAXException, IOException {
		ClassLoader classLoader = FopPdfaGenerator.class.getClassLoader();
		File configFile = new File(classLoader.getResource(FOP_XCONF).getFile());
		
		Properties prop = new Properties();
		InputStream is = new FileInputStream(classLoader.getResource(CONFIG_PROPERTIES).getFile());
		prop.load(is);
		
		FopFactory fopFactory = FopFactory.newInstance(configFile);
		String outputFile = prop.getProperty("OUTPUT_PATH") + File.separator + "myfile.pdf";
		OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outputFile)));
		
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		foUserAgent.setAccessibility(true);
		foUserAgent.getRendererOptions().put("pdf-a-mode", "PDF/A-1b");
		
		try {
		    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
		    TransformerFactory factory = TransformerFactory.newInstance();
		    Transformer transformer = factory.newTransformer();
		    Source src = new StreamSource(classLoader.getResource(FOP_FO_FILE).getFile());
		    Result res = new SAXResult(fop.getDefaultHandler());
		    transformer.transform(src, res);
		} catch (TransformerException ex) {
			LOGGER.log(Level.SEVERE, "Could not convert to PDF/A", ex);
		} finally {
		    out.close();
		}
		
	}
}
