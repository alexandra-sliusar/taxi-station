package ua.taxistation.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.taxistation.entity.Request;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.LocaleMessage;

public class RequestsHistoryTag extends TagSupport {
	/**
	 * java tag class to output list of unprocessed request to dispatcher
	 */
	private static final long serialVersionUID = -8502435357979179441L;
	private List<Request> requestList;

	public void setRequestList(List<Request> requestList) {
		this.requestList = requestList;
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
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.DATE)
						+ TagConstants.tableHeaderCellEndTag)

				.append(TagConstants.tableRowEndTag).append(TagConstants.tableHeadEndTag)
				.append(TagConstants.tableBodyStartTag);

		for (Request request : requestList) {
			sb.append(TagConstants.tableRowStartTag);
			sb.append(TagConstants.tableCellStartTag)
					.append(TagConstants.linkToRequestStartTag + request.getId() + TagConstants.linkToRequestEnd)
					.append(request.getId()).append(TagConstants.linkEndTag + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + request.getPickup() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + request.getDestination() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + request.getDateOfRequest() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag);
			for (CarCharacteristics carchar : request.getCarCharacteristics()) {
				sb.append(LocaleManager.getString(carchar.getLocaleKey())).append(TagConstants.nextLineTag);
			}
			sb.append(TagConstants.tableCellEndTag).append(TagConstants.tableRowEndTag);
		}

		sb.append(TagConstants.tableBodyEndTag).append(TagConstants.tableEndTag);
		return sb.toString();
	}

}