package suggestions.example.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 16-9-8.
 */
public class CitXml implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<XProperty> xProperties = new ArrayList<XProperty>();
    private List<XMethod> xMethods = new ArrayList<XMethod>();
    private List<XNode> xNodes = new ArrayList<XNode>();

    public List<XProperty> getXProperties() {
        return xProperties;
    }

    public void setXProperties(List<XProperty> list) {
        this.xProperties = list;
    }

    public List<XMethod> getXMethods() {
        return xMethods;
    }

    public void setXMethods(List<XMethod> list) {
        this.xMethods = list;
    }

    public List<XNode> getXNodes() {
        return xNodes;
    }

    public void setXNodes(List<XNode> list) {
        this.xNodes = list;
    }
}
