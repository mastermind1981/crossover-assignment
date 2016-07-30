package com.dev.frontend.panels.list;

import java.util.List;

import com.dev.frontend.dto.CustomerDto;
import com.dev.frontend.services.Services;

public class CustomerDataModel extends ListDataModel {
	private static final long serialVersionUID = 7526529951747613655L;

	public CustomerDataModel() {
		super(new String[] { "Code", "Name", "Phone", "Current Credit" }, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_CUSTOMER;
	}

	/*
	 * This method use list returned by Services.listCurrentRecords and should
	 * convert it to array of rows each row is another array of columns of the
	 * row
	 */
	@Override
	public String[][] convertRecordsListToTableModel(List<Object> list) {
		String[][] customerData = null;
		if (list != null) {
			customerData = new String[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				CustomerDto customerDto = (CustomerDto) list.get(i);
				customerData[i][0] = customerDto.getCode();
				customerData[i][1] = customerDto.getName();
				customerData[i][2] = customerDto.getPhone1();
				customerData[i][3] = String.format("%.2f", customerDto.getCurrentCredit());
			}
		}
		return customerData;
	}
}
