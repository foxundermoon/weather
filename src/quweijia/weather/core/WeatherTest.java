package quweijia.weather.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class WeatherTest {
	static String xmlStr="";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// test1();
		encodeTest();

	}

	private static void test1() {
		// TODO Auto-generated method stub
		Weather tq = new Weather();
		try {
			String xml = tq.getXmlStringFromUrl(tq.testUrl);
			p(xml);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void encodeTest() {
		try {
			p(URLEncoder.encode("北京", "GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void p(String str) {
		// TODO Auto-generated method stub
		System.out.println(str);
	}

	private static void xmlPaseTest() {
	
		
		
		

		try {
			Weather tq = new Weather();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // 取得DocumentBuilderFactory实例
			DocumentBuilder builder;
				builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(tq.getXmlStringFromUrl(tq.testUrl)
					.getBytes()));
			Element root = doc.getDocumentElement();
			p(root.getElementsByTagName("power1").item(0).getNodeValue());

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
