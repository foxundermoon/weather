package quweijia.weather.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;

public class Weather {
	// http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0
	public static final String tianqiApiUrl = "http://php.weather.sina.com.cn/xml.php?password=DJOYnieT8234jlsK&";
	private static final String WEATHER_BASE_URL = "http://php.weather.sina.com.cn/xml.php?password=DJOYnieT8234jlsK";
	private HttpClient hc;
	public String testUrl = "http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0";
	private String cityName;
	private String cityNameGb2312;
	private Map<String, String> weatherMap;

	public Weather() {
		hc = new DefaultHttpClient();
		weatherMap = new HashMap<String, String>();
	}

	public void setCity(String city) {
		// TODO Auto-generated method stub
		cityName = city;
	}

	public boolean checkout() {
		// TODO Auto-generated method stub

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance(); // 取得DocumentBuilderFactory实例
			DocumentBuilder builder = factory.newDocumentBuilder();// 从factory获取DocumentBuilder实例
			try {
				// 创建xml文档对象
				Document doc = builder.parse(new ByteArrayInputStream(
						getXmlStringFromUrl(getWeatherApiUrl()).getBytes()));
				// 解析xml放入hashmap中
				NodeList nodeList = doc.getElementsByTagName("Weather").item(0).getChildNodes();
				for(int i=0;i<nodeList.getLength();i++){
					Node tmp = nodeList.item(i);
					weatherMap.put(tmp.getNodeName(), tmp.getTextContent());
				}
				return true;

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private String getWeatherApiUrl() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return getWeatherApiUrl(1);
	}

	private String getWeatherApiUrl(int type)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String params = null;
		switch (type) {
		case 1:
			params = "city=" + this.getCityByGb2312() + "&day=0";

		}
		return WEATHER_BASE_URL + "&" + params;
	}

	@SuppressLint("NewApi")
	public String getXmlStringFromUrl(String strUrl)
			throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(strUrl);
		ResponseHandler responseHandler = new BasicResponseHandler();
		// 获取xml文件
		String content = hc.execute(httpGet, responseHandler);
		// 把xml文件编码转换为utf-8
		return new String(content.getBytes("ISO-8859-1"),
				Charset.forName("UTF-8"));

	}

	public String getCityByGb2312() throws UnsupportedEncodingException {
		String city1 = getCity();
		if (city1 == null || "".equalsIgnoreCase(city1)) {
			city1 = "济南";
		}
		return URLEncoder.encode(city1, "GB2312");
	}

	private String getCity() {
		// TODO Auto-generated method stub
		return cityName;
	}

	public String testNet() {
		try {
			return getXmlStringFromUrl(testUrl);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, String> getWeatherMap() {
		// TODO Auto-generated method stub
		return weatherMap;
	}

}
