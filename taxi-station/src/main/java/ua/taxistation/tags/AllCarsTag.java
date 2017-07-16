package ua.taxistation.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.taxistation.entity.Car;
import ua.taxistation.entity.Request;
import ua.taxistation.entity.enums.CarCharacteristics;
import ua.taxistation.utilities.LocaleManager;

public class AllCarsTag extends TagSupport {
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
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.ID)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag
						+ LocaleManager.BUNDLE.getString(LocaleManager.DRIVER_LOGIN)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.CAR_NUMBER)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.CAR_MODEL)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.CAR_COLOR)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag
						+ LocaleManager.BUNDLE.getString(LocaleManager.CAR_CHARACTERISTICS)
						+ TagConstants.tableHeaderCellEndTag)
				.append(TagConstants.tableHeaderCellStartTag + LocaleManager.BUNDLE.getString(LocaleManager.STATUS)
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
				sb.append(LocaleManager.BUNDLE.getString(carchar.getLocaleKey())).append(TagConstants.nextLineTag);
			}
			sb.append(TagConstants.tableCellStartTag + LocaleManager.BUNDLE.getString(car.getCarStatus().getLocaleKey())
					+ TagConstants.tableCellEndTag);
			sb.append(TagConstants.tableCellEndTag).append(TagConstants.tableRowEndTag);
		}

		sb.append(TagConstants.tableBodyEndTag).append(TagConstants.tableEndTag);
		return sb.toString();
	}

}
