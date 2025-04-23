/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import ict.bean.*;
import ict.util.*;

/**
 *
 * @author local_user
 */
public class UserTag extends TagSupport {

    private UserBean userBean;
    private ShopBean shopBean;
    private WarehouseBean warehouseBean;
    private RegionBean regionBean;
    private CodeManager cM;

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public void setShopBean(ShopBean shopBean) {
        this.shopBean = shopBean;
    }

    public void setWarehouseBean(WarehouseBean warehouseBean) {
        this.warehouseBean = warehouseBean;
    }

    public void setRegionBean(RegionBean regionBean) {
        this.regionBean = regionBean;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.println("<tr>");
            writer.println("<td>" + regionBean.getRegionName() + "</td>");
            if (shopBean != null) {
                writer.println("<td>" + shopBean.getShopName() + "</td>");
            } else if (warehouseBean != null) {
                writer.println("<td>" + warehouseBean.getWarehouseName() + "</td>");
            } else {
                writer.println("<td>non applicable</td>");
            }
            writer.println("<td>" + userBean.getDisplayName() + "</td>");
            writer.println("<td>" + cM.getRole(userBean.getRole()) + "</td>");
            writer.println("</tr>");
        } catch (IOException e) {
            throw new JspException("Error writing to output stream", e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
