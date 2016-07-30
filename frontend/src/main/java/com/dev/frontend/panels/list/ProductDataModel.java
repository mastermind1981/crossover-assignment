package com.dev.frontend.panels.list;

import java.util.List;

import com.dev.frontend.constants.Constants;
import com.dev.frontend.dto.ProductDto;
import com.dev.frontend.services.Services;

public class ProductDataModel extends ListDataModel {
	private static final long serialVersionUID = 7526529951747614655L;

	public ProductDataModel() {
		super(new String[] { "Code", "Description", "Price", "Quantity" }, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_PRODUCT;
	}

	/*
	 * This method use list returned by Services.listCurrentRecords and should
	 * convert it to array of rows each row is another array of columns of the
	 * row
	 */
	@Override
	public String[][] convertRecordsListToTableModel(List<Object> list) {
		String[][] sampleData = null;
		if (list != null) {
			sampleData = new String[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				ProductDto productDto = (ProductDto)list.get(i);
				sampleData[i][0] = productDto.getCode();
				sampleData[i][1] = productDto.getDescription();
				sampleData[i][2] = Constants.df.format(productDto.getPrice());
				sampleData[i][3] = productDto.getQuantity() + "";
			}
		}
		return sampleData;
	}
}
