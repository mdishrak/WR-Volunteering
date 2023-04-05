package sqltest2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlMan {

	ArrayList<Animals> animalList = new ArrayList<Animals>();
	ArrayList<Tasks> taskList = new ArrayList<Tasks>();
	ArrayList<Treatments> treatmentList = new ArrayList<Treatments>();

	public void readDataBase() throws Exception {
		Connection connect = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ewr?" + "user=root&password=Rakish,1202");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from ewr.animals");

			while (resultSet.next()) {
				String aid = resultSet.getString(1);
				String aname = resultSet.getString(2);
				String aSpec = resultSet.getString(3);
				// System.out.println("ID:"+aid+" || NAME:"+aname + " || Species: "+ aSpec);

				Animals tempAnimal = new Animals(aid, aname, aSpec);
				animalList.add(tempAnimal);
			}

			resultSet = statement.executeQuery("select * from ewr.tasks");

			int indexTrack = 1;
			while (resultSet.next()) {
				String tid = resultSet.getString(1);
				String tDesc = resultSet.getString(2);
				int tDur = Integer.parseInt(resultSet.getString(3));
				String aMax = resultSet.getString(4);
				// System.out.println(" Task ID :"+tid+" || T Desc: "+tDesc + " || T Dur : "+
				// tDur + " || MaxWindow: "+ aMax );
				indexTrack++;
				Tasks tempTask = new Tasks(tid, tDesc, tDur, aMax);
				taskList.add(tempTask);
			}

			AddExtraTasks(indexTrack);

			resultSet = statement.executeQuery("select * from ewr.treatments");
			while (resultSet.next()) {
				String trId = resultSet.getString(1);
				String aid = resultSet.getString(2);
				String tid = resultSet.getString(3);
				String startHour = resultSet.getString(4);
				// System.out.println( " Treatment ID: "+ trId+ " || Animal ID :"+aid+" || Task
				// Id: "+tid + " || Start Hour : "+ startHour );

				Treatments tempTreatment = new Treatments(trId, aid, tid, startHour);
				treatmentList.add(tempTreatment);
			}

			// System.out.println(animalList.size());

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}
		}

	}

	public void AddExtraTasks(int lastId) {
		Tasks tempTask = new Tasks(String.valueOf(lastId), "feeding beaver", 5, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "feeding coyote", 15, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "feeding porcupine", 5, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "feeding raccoon", 5, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "feeding fox", 10, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "clean cage: coyote", 5, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "clean cage: beaver", 5, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "clean cage: porcupine", 10, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "clean cage: raccoon", 5, "2");
		taskList.add(tempTask);
		lastId++;

		tempTask = new Tasks(String.valueOf(lastId), "clean cage: fox", 5, "2");
		taskList.add(tempTask);
	}

	public ArrayList<Treatments> mergedList() {
		ArrayList<Treatments> tmpArrayList = new ArrayList<Treatments>();

		for (Treatments t : treatmentList) {
			String thisanimid = t.aniID;
			String thisanimName = "";
			for (Animals a : animalList)
				if (thisanimid.equals(a.animalID))
					thisanimName = a.animalName;

			String thisTaskId = t.taskID;
			String thisTaskName = "";
			for (Tasks a : taskList)
				if (thisTaskId.equals(a.taskID))
					thisTaskName = a.taskDesc;

			tmpArrayList.add(new Treatments(t.treatID, t.taskID, thisanimName, thisTaskName, t.startTime));

		}
		return tmpArrayList;

	}

}
