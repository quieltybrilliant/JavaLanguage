package javaLanguage.common.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @Author: guang
 * @Date: 2022/8/13
 * @Desc: XML DOM解析器辅助类
 */
public class DomUtil {

    private static Logger _logger = LoggerFactory.getLogger(DomUtil.class);

    /**
     * 生成XML文件的实例。
     * @param classPathXmlFile
     * @return
     */
    public static Document createDocument(String classPathXmlFile) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder dom = domFactory.newDocumentBuilder();
            document = dom.parse(DomUtil.class.getClassLoader().getResourceAsStream(classPathXmlFile));
        } catch (Exception e) {
            _logger.error( String.format("create Document of xml file[%s] occurs error", classPathXmlFile), e);
        }

        return document;
    }
}