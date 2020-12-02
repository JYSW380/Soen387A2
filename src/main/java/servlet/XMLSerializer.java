package servlet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class XMLSerializer {
    public static <T> String Serialize(T t) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(t.getClass());
        StringWriter sw = new StringWriter();
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        m.marshal(t, sw);
        return sw.toString();
    }
}