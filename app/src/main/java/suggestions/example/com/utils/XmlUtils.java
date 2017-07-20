package suggestions.example.com.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Xml;



import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import suggestions.example.com.bean.CitXml;
import suggestions.example.com.bean.XMethod;
import suggestions.example.com.bean.XNode;
import suggestions.example.com.bean.XProperty;

/**
 * Created by yang on 16-9-8.
 */
public class XmlUtils {

    private static final String TAG = "XmlUtils";
    private static boolean node_isFromStorage = false;
    private static CitXml mxml = new CitXml();
    private static List<XProperty> xProperties = mxml.getXProperties();
    private static List<XMethod> xMethods = mxml.getXMethods();
    private static List<XNode> xNodes = mxml.getXNodes();

    public CitXml getMxml(Context mContext) {
        XmlPullParser xrp = null;
        InputStream is = null;
        if (is == null) {
            try {
                is = mContext.getAssets().open("cit_conf_node.xml");
                Log.i(TAG, "xsp_cit_conf_node is found! in CIT");
                node_isFromStorage = false;
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "error is " + e.toString());
                Log.e(TAG, "xsp_cit_conf_node.xml has IO error in MyXmlUtils");
            }
        }
        try {
            xrp = Xml.newPullParser();
            xrp.setInput(is, "utf-8");
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG
                        && "properties".equals(xrp.getName())) {
                    getProperties(xrp, "properties", xProperties);
                } else if (xrp.getEventType() == XmlResourceParser.START_TAG
                        && "methods".equals(xrp.getName())) {
                    getMethods(xrp, "methods", xMethods);
                } else if (xrp.getEventType() == XmlResourceParser.START_TAG
                        && "nodes".equals(xrp.getName())) {
                    getNodes(xrp, "nodes", xNodes);
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            Log.e(TAG, "xsp_May be the config file has XmlPullParser error!_all");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "xsp_May be the config file has IO error!_all");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.i("config", e.getMessage());
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
        }
        return mxml;
    }

    private void getProperties(XmlPullParser xrp, String pNode, List<XProperty> list) {
        try {
            while (!(xrp.getEventType() == XmlResourceParser.END_TAG &&
                    pNode.equals(xrp.getName()))) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG &&
                        "property".equals(xrp.getName())) {
                    String name = xrp.getAttributeValue(0);
                    String value = xrp.getAttributeValue(1);
                    XProperty xproperty = new XProperty();
                    xproperty.setName(name);
                    xproperty.setValue(value);
                    list.add(xproperty);
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, "xsp_May be the config file has xmlpullparser error!_property");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "xsp_May be the config file has IO error!_property");
            e.printStackTrace();
        }
    }

    private void getMethods(XmlPullParser xrp, String pNode, List<XMethod> list) {
        try {
            while (!(xrp.getEventType() == XmlResourceParser.END_TAG &&
                    pNode.equals(xrp.getName()))) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG &&
                        "method".equals(xrp.getName())) {
                    String name = xrp.getAttributeValue(0);
                    String value = xrp.getAttributeValue(1);
                    XMethod xmethod = new XMethod();
                    xmethod.setName(name);
                    xmethod.setValue(value);
                    list.add(xmethod);
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, "xsp_May be the config file has xmlpullparser error!_xmethod");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "xsp_May be the config file has IO error!_xmethod");
            e.printStackTrace();
        }
    }

    private void getNodes(XmlPullParser xrp, String pNode, List<XNode> list) {
        try {
            while (!(xrp.getEventType() == XmlResourceParser.END_TAG &&
                    pNode.equals(xrp.getName()))) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG &&
                        "node".equals(xrp.getName())) {
                    String name = xrp.getAttributeValue(0);
                    String operation = xrp.getAttributeValue(1);
                    String permission = xrp.getAttributeValue(2);
                    String value = xrp.getAttributeValue(3);
                    XNode xnode = new XNode();
                    xnode.setName(name);
                    xnode.setValue(value);
                    xnode.setPermission(permission);
                    xnode.setOperation(operation);
                    list.add(xnode);
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, "xsp_May be the config file xmlpullparser has error!_xnode");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "xsp_May be the config file has  IO error!_xnode");
            e.printStackTrace();
        }
    }

    public static String getHWVersion() {
        String hw_version = null;
        String hw_version_promission = null;
        if (xNodes != null) {
            for (XNode xn : xNodes) {
                String name = xn.getName();
                if ("hw_version".equals(name)) {
                    hw_version = xn.getValue();
                    hw_version_promission = xn.getPermission();
                }
            }
        }
        String hwversion = null;
        if (hw_version != null && "O_RDONLY".equals(hw_version_promission)) {
            hwversion = CommonDrive.getHWVersionOld(hw_version);
            Log.e(TAG, "_xsp_hw_version = " + hw_version
                    + ", _hwversion = " + hwversion);
        } else {
            Log.e(TAG, "xsp_hw_version is null or the promission is not O_RDONLY");
            hwversion = CommonDrive.getHWVersion();
        }
        return hwversion;
    }

    public static String getSoftwareVersion(Context context) {
        String software_title = null;
        /*String software_time = null;
        String software_buildtime = null;*/
        if (xProperties != null) {
            for (XProperty xpp : xProperties) {
                String name = xpp.getName();
                if ("software_title".equals(name)) {
                    software_title = xpp.getValue();
                }
                /*if ("software_time".equals(name)) {
                    software_time = xpp.getValue();
                }
                if ("software_buildtime".equals(name)) {
                    software_buildtime = xpp.getValue();
                }*/
            }
        }
        String softwareTitle = null;
        /*String softwareTime = null;
        String softwareBuildTime = null;*/
        if (software_title != null) {
            softwareTitle = getSystemproString(context,software_title);
            Log.e(TAG, "_xsp_software_title = " + software_title
                    + ", _softwareTitle = " + softwareTitle);
        } else {
            softwareTitle = getSystemproString(context,"ro.product.version.software");
            Log.e(TAG, "_xsp_software_title is null ");
        }
        /*if (software_time != null) {
            softwareTime = getSystemproString(software_title);
            Log.e(TAG, "_xsp_software_time = " + software_time
                    + ", _softwareTime = " + softwareTime);
        } else {
            softwareTime = getSystemproString("ro.build.version.incremental");
            Log.e(TAG, "_xsp_software_time is null");
        }
        if (software_buildtime != null) {
            softwareBuildTime = getSystemproString(software_buildtime);
            Log.e(TAG, "_xsp_software_buildtime = " + software_buildtime
                    + ", _softwareBuildTime = " + softwareBuildTime);
        } else {
            softwareBuildTime = getSystemproString("ro.product.date");
            Log.e(TAG, "_xsp_software_buildtime is null");
        }*/
        return softwareTitle;
    }
    public static String getSystemproString(Context context,String property) {
        /*String value = SystemProperties.get(property, getString(R.string.cit_info_default));
        return TextUtils.isEmpty(value) ?
                getProperty(this, property, getString(R.string.cit_info_default)) : value;*/
        return getProperty(context, property, "unknown");
    }

    public static String getProperty(Context context, String key, String defValue) {
        try {
            Class<?> SystemProperties = context.getClassLoader().loadClass(
                    "android.os.SystemProperties");
            Method get = SystemProperties.getMethod("get", new Class[]{
                    String.class, String.class
            });
            return (String) get.invoke(null, new Object[]{
                    key, defValue
            });
        } catch (Exception e) {
            Log.e("Exception", "SystemProperties", e);
        }
        return null;
    }
}
