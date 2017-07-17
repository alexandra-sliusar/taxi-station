package ua.taxistation.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.taxistation.entity.Order;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.LocaleMessage;

public class ClientOrderHistoryTag extends TagSupport {
	/**
	 * java tag class to output list of orders according to client
	 */
	private static final long serialVersionUID = -5337811650697220019L;
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
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.ID)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.PICKUP)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.DESTINATION)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.CAR_NUMBER)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.CAR_MODEL)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.CAR_COLOR)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.DATE)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.STATUS)
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
					.append(TagConstants.tableCellStartTag + order.getCar().getNumber() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getCar().getModel() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getCar().getColor() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + order.getRequest().getDateOfRequest()
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag
							+ LocaleManager.getString(order.getOrderStatus().getLocaleKey())
							+ TagConstants.tableCellEndTag)
					.append(TagConstants.tableRowEndTag);
		}
		sb.append(TagConstants.tableBodyEndTag).append(TagConstants.tableEndTag);
		return sb.toString();
	}

}
