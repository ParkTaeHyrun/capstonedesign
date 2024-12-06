package com.gwangju.capstonedesign;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiExplorer{

    public static List<Map<String, Object>> apicall(String startdate, String enddate) throws IOException, SAXException, ParserConfigurationException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/EqkInfoService/getEqkMsg"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=pMymGMt5TX3rHZyVz9agpEYhaZy678NqdYO0wNiji6THFStVZkXJUFfpOjgxNRu7E0d8yBetWXl3H68bcG60bA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*요청자료형식(XML/JSON)*/
        urlBuilder.append("&" + URLEncoder.encode("fromTmFc", "UTF-8") + "=" + URLEncoder.encode(startdate, "UTF-8")); /*시간(년월일)*/
        urlBuilder.append("&" + URLEncoder.encode("toTmFc", "UTF-8") + "=" + URLEncoder.encode(enddate, "UTF-8")); /*시간(년월일)*/
        URL url = new URL(urlBuilder.toString());
        System.out.println(url);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(String.valueOf(url));
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("item");

        String img = null;
        String loc = null;
        String tmEqk = null;
        List<Map<String, Object>> resultitem = new ArrayList<Map<String, Object>>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            Element eElement = (Element) nNode;
            img = getTagValue("img", eElement);
            loc = getTagValue("loc", eElement);
            tmEqk = getTagValue("tmEqk", eElement);

            System.out.println("이미지 주소 : " + img);
            System.out.println("위치 : " + loc);
            System.out.println("시간 : " + tmEqk);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("img", img);
            result.put("loc", loc);
            result.put("tmEqk", tmEqk);
            resultitem.add(result);
        }
        return resultitem;
    }

    public static String getTagValue(String tag, Element eElement) {
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        result = nlList.item(0).getTextContent();
        return result;
    }

    public static String getTagValue(String tag, String childTag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        for(int i = 0; i < eElement.getElementsByTagName(childTag).getLength(); i++) {

            //result += nlList.item(i).getFirstChild().getTextContent() + " ";
            result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
        }

        return result;
    }
}
