package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.Assertion;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class FlywayDBUnit_Tests extends TestCase {
	private static FlatXmlDataSet loadedDataSet;
	IDatabaseConnection iconnection = null;
	DatabaseConnection connection = null;
	Connection jdbcConnection = null;
	String[] tablenames = null;
	StringBuffer result = null;
	IDataSet fullDataSet = null;
	IDataSet actualDataset = null;
	IDataSet expectedDataSet = null;
	QueryDataSet partDS = null;
    private static int counter = 0;
	
	protected IDatabaseConnection getConnection() throws Exception {
		Class driverClass = Class.forName("com.mysql.cj.jdbc.Driver");
		jdbcConnection = DriverManager.getConnection(
				"jdbc:mysql://isg-linux.southeastasia.cloudapp.azure.com/flyway_schema_temp?autoReconnect=true&useSSL=false",
				"root", "Kitchen@123");
		connection = new DatabaseConnection(jdbcConnection, "flyway_schema_temp");
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
		return connection;
	}

	protected static IDataSet getDataSet() throws Exception {
		System.out.println("Entering getDataSet() method****");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(false);
		builder.setCaseSensitiveTableNames(false);
		builder.setDtdMetadata(false);
		loadedDataSet = builder
				.build(new FileInputStream("InitialTest-data-ToBeLoaded.xml"));
		System.out.println("Exiting getDataSet() method****");
		return loadedDataSet;
	}


	@Override
	public void setUp() throws Exception {
		super.setUp();
		connection = (DatabaseConnection) getConnection();
		
		if (counter == 0)
		{
			//Export database before tests run
			fullDataSet = connection.createDataSet();
	        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("InitialDB-State.xml"));
	        
			//load tests data before testing commences
	        DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
	        counter++;
		}
	}

	@Test
	public void testCheckXMLRowCount() throws Exception {
		int rowCount = loadedDataSet.getTable("login").getRowCount();
		System.out.println("rowCount :" + rowCount);
		assertEquals(2, rowCount);
	 }

	 @Test 
	 public void testCheckXMLDataNonNUllability2() throws Exception {
		 assertNotNull(loadedDataSet); 
	 }
	 
	 @Test 
	 public void testCheckXMLDataNonNUllability3() throws Exception {
		 assertNotNull(loadedDataSet); 
	 }

	 @Test 
	 public void testCheckXMLDataNonNUllability4() throws Exception {
		 assertNotNull(loadedDataSet); 
	 }
	 
	 @Test 
	 public void testCheckActualvsLoaded5() throws Exception {
	        // Fetch database data after executing your code
	        IDataSet databaseDataSet = getConnection().createDataSet();
	        ITable actualTable = databaseDataSet.getTable("login");
	        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actualTable, 
	        		new String[]{"empcode","loginname","password","loginenabled"});
	        
	        // Load expected data from an XML dataset
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(false);
			builder.setCaseSensitiveTableNames(false);
			builder.setDtdMetadata(false);
	        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("InitialTest-data-ToBeLoaded.xml"));
	        ITable expectedTable = expectedDataSet.getTable("login");

	        // Assert actual database table match expected table
	        Assertion.assertEquals(expectedTable, filteredTable);
	 }
}
