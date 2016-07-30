package com.dev.frontend.panels.list;

import java.util.List;

import com.dev.frontend.constants.Constants;
import com.dev.frontend.dto.SaleOrderDto;
import com.dev.frontend.services.Services;


public class SalesOrderDataModel extends ListDataModel
{
	private static final long serialVersionUID = 7526529951747614655L;

	public SalesOrderDataModel() 
	{
		super(new String[]{"Order Number","Customer","Total Price"}, 0);
	}

	@Override
	public int getObjectType() {
		return Services.TYPE_SALESORDER;
	}

	/*
	 * This method use list returned by Services.listCurrentRecords and should convert it to array of rows
	 * each row is another array of columns of the row
	 */
	@Override
	public String[][] convertRecordsListToTableModel(List<Object> list) 
	{
		String[][] sampleData = null;
		if(list != null && list.size() > 0){
			sampleData = new String[list.size()][3];
			for(int i=0;i<list.size();i++){
				SaleOrderDto saleOrderDto = (SaleOrderDto)list.get(i);
				sampleData[i][0] = saleOrderDto.getOrderNumber();
				sampleData[i][1] = "("+saleOrderDto.getCustomer().getCode()+")"+saleOrderDto.getCustomer().getName();
				sampleData[i][2] = Constants.df.format(saleOrderDto.getTotalPrice());
			}
		}
		return sampleData;
	}
}
