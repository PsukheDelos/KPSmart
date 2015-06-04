package kps.tests.distribution;

import static org.junit.Assert.*;
import kps.distribution.network.Company;

import org.junit.Test;

public class CompanyTests {
	@Test
	public void companiesWithTheSameNameAreEqual(){
		assertEquals(new Company("name"), new Company("name"));
	}

	@Test
	public void companyIsEqualToItself(){
		Company company = new Company("name");
		assertEquals(company, company);
	}

	@Test
	public void companyIsNotEqualToCompanyWithDifferentName(){
		assertNotEquals(new Company("name1"), new Company("name2"));
	}
}
