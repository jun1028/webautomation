package com.test.fixture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Factory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.test.basetest.data.SuiteData;
import com.test.common.DataConsts;
import com.test.common.ExcelReader;
import com.test.common.FormatUtil;
import com.test.common.IExcelReader;

public class ActionFixtureFactory {

	private List shNames = null;

	@Parameters( { "filename" })
	@Factory
	public Object[] createActionFixture(@Optional("testcasebc.xls") String fName) {
		fName = SuiteData.rootDirOfTestcase + File.separator + fName;
		IExcelReader excel = new ExcelReader(fName);
		shNames = (List) excel.getAllSheetNames();
		excel.close();
		List resultList = new ArrayList();
		for (int i = 0; i < shNames.size(); i++) {
			String shName = (String) shNames.get(i);
			/**if sheet name include  _testcase*/
			if (shName.contains(DataConsts.TAG_SHEETTESTCASESUF)) { 
				if (existParamsSh(shName)) { // IF exist parameter sheet
					resultList
							.add(new TestActionFixtureWithParam(fName, shName));
				} else {
					resultList.add(new TestActionFixture(fName, shName));
				}
			}
		}
		Object[] result = new Object[resultList.size()];
		for (int j = 0; j < resultList.size(); j++) {
			result[j] = resultList.get(j);
		}
		return result;
	}

	/**
	 * if sheet name contains "_params", it means parameter sheet!
	 * 
	 * @param shName
	 * @return
	 */
	public boolean existParamsSh(final String shName) {
		boolean bResult = false;
		String temp = shName;
		temp = FormatUtil.formatShName(temp);
		temp = temp + DataConsts.TAG_SHEETPARAMSUF;
		for (int i = 0; i < shNames.size(); i++) {
			if (temp.equalsIgnoreCase((String) shNames.get(i)))
				bResult = true;
		}
		return bResult;
	}

}
