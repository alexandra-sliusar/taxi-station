package ua.taxistation.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.taxistation.entity.Order;
import ua.taxistation.utilities.LocaleManager;

public class DriverOrderHistoryTag extends TagSupport {
	private List<Order> orderList;

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(showUserData());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	private String showUserData() {
		StringBuffer sb = new StringBuffer()
				.append(TagConstants.tableStartTag + TagConstants.tableHeadStartTag + TagConstants.tableRowStartTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.ID)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.PICKUP)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.DESTINATION)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag
						+ LocaleManager.BUNDLE.getString(LocaleManager.PHONE_NUMBER)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.DATE)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.STATUS)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableRowEndTag).append(TagConstants.tableHeadEndTag)
				.append(TagConstants.tableBodyStartTag);

		for (Order order : orderList) {
			sb.append(TagConstants.tableRowStartTag);
			sb.append(TagConstants.tableCellStartTag + order.getId() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getRequest().getPickup()
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getRequest().getDestination()
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getRequest().getUser().getPhonenumber()
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getRequest().getDateOfRequest()
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag
							+ LocaleManager.BUNDLE.getString(order.getOrderStatus().getLocaleKey())
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableRowEndTag);
		}
		sb.append(TagConstants.tableBodyEndTag).append(TagConstants.tableEndTag);
		return sb.toString();
	}

}
