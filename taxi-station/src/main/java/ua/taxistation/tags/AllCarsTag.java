package ua.taxistation.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.taxistation.entity.Car;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.LocaleMessage;

public class AllCarsTag extends TagSupport {
	/**
	 * java tag class to output list of cars existing in the database
	 */
	private static final long serialVersionUID = 4418976297672039363L;
	private List<Car> carList;

	public void setCarList(List<Car> carList) {
		this.carList = carList;
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
				.append(TagConstants.tableHeaderCellStartTag
						+ LocaleManager.BUNDLE.getString(LocaleManager.getString(LocaleMessage.DRIVER_LOGIN))
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.CAR_NUMBER)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.CAR_MODEL)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.CAR_COLOR)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag
						+ LocaleManager.getString(LocaleMessage.CAR_CHARACTERISTICS)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.getString(LocaleMessage.STATUS)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableRowEndTag).append(TagConstants.tableHeadEndTag)
				.append(TagConstants.tableBodyStartTag);

		for (Car car : carList) {
			sb.append(TagConstants.tableRowStartTag);
			sb.append(TagConstants.tableCellStartTag).append(car.getId() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + car.getDriver().getLogin() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + car.getNumber() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + car.getModel() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag + car.getColor() + TagConstants.tableCellEndTag)
					.append(TagConstants.tableCellStartTag);
			for (CarCharacteristics carchar : car.getCarCharacteristics()) {
				sb.append(LocaleManager.getString(carchar.getLocaleKey())).append(TagConstants.nextLineTag);
			}
			sb.append(TagConstants.tableCellStartTag + LocaleManager.getString(car.getCarStatus().getLocaleKey())
					+ TagConstants.tableCellEndTag);
			sb.append(TagConstants.tableCellEndTag).append(TagConstants.tableRowEndTag);
		}

		sb.append(TagConstants.tableBodyEndTag).append(TagConstants.tableEndTag);
		return sb.toString();
	}

}
