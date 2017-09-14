package com.reimburse.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Reimbursement.reimbursementStatus;
import com.reimburse.service.Service;
import com.reimburse.pojos.Worker;
import com.reimburse.util.ConnectionFactory;

// NOTE: SQL is 1-indexed rather than 0-indexed
// FIXME remove redundant code

public class DaoImpl implements Dao {

	public static boolean connection;
	final static Logger logger = Logger.getLogger(DaoImpl.class);

	// Attempt to connect to database upfront.
	static {
		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			connection = true;

		} catch (Exception e) {
			logger.info("Error! Could not connect to database!");
			logger.error(e);
			connection = false;
		}

	}

	// LocalDateTime.parse(day) reference: 2017-08-30T13:31:18.553
	// Database reference: TO_TIMESTAMP(:ts_val, 'YYYY-MM-DD_HH24:MI:SS')
	public String getFormattedTimestamp(LocalDateTime day) {
		// Replace the 'T' with a space, and cut off the milliseconds

		if (day == null)
			return null;

		String time = day.toString();

		// Cut off the three millisecond digits and the decimal place
		time = time.substring(0, (time.length() - 1) - 3);

		// Replace the 'T' with ' '
		int tIndex = time.indexOf('T');
		char[] timeArray = time.toCharArray();
		timeArray[tIndex] = ' ';

		return String.copyValueOf(timeArray);
	}

	public LocalDateTime fromFormattedTimestamp(String str) {
		if (str == null || str.equals("null"))
			return null;

		// Put the 'T' back in index 10 where it belongs
		char[] timeArray = str.toCharArray();
		timeArray[10] = 'T';

		return LocalDateTime.parse(String.copyValueOf(timeArray));
	}

	// Need to set either String, int, double, LocalDate, or boolean
	private void setPreparedStatement(int i, PreparedStatement ps, Object obj) throws SQLException {
		if (obj instanceof String)
			ps.setString(i, (String) obj);
		else if (obj instanceof Integer)
			ps.setInt(i, (int) obj);
		else if (obj instanceof Double)
			ps.setDouble(i, (double) obj);
		else if (obj instanceof Boolean)
			ps.setInt(i, (Boolean) obj ? 1 : 0); // SQL does not have booleans.
													// Saved as an int
		else if (obj instanceof LocalDateTime)
			ps.setString(i, getFormattedTimestamp((LocalDateTime) obj));
		else
			logger.info("Error. Illegal data type");
	}

	///////////////////////////////////////////////////////////////////////////////
	// CREATE methods
	///////////////////////////////////////////////////////////////////////////////

	private Integer create(String sql, String[] key, ArrayList<Object> objects) {
		Integer id = null;

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			for (int i = 0; i < objects.size(); i++)
				setPreparedStatement(i + 1, ps, objects.get(i));

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();

			ResultSet pk = ps.getGeneratedKeys();
			while (pk.next())
				id = pk.getInt(1);

			conn.commit();

		} catch (SQLException e) {
			logger.info("Database error on create");
			logger.error(e);
		}

		return id;

	}

	public Worker createWorker(String firstName, String lastName, String email, String username, String password,
			boolean isManager, boolean isHired) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "INSERT INTO worker(first_name, last_name, email, username, password, is_manager, is_hired)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)";
		String[] key = { "worker_id" };

		objects.add((Object) firstName);
		objects.add((Object) lastName);
		objects.add((Object) email);
		objects.add((Object) username);
		objects.add((Object) password);
		objects.add((Object) isManager);
		objects.add((Object) isHired);

		Integer id = create(sql, key, objects);

		if (id != null)
			return new Worker(id, firstName, lastName, email, username, password, isManager, isHired);

		return null;

	}

	@Override
	public Reimbursement createReimbursement(int submitterId, reimbursementStatus status, LocalDateTime submitDate,
			String description, BigDecimal ammount) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "INSERT INTO reimbursement(submitter_id_fk, status_id_fk, submit_date, description, ammount)"
				+ " VALUES(?, ?, TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?)";
		String[] key = { "reimbursement_id" };

		objects.add((Object) submitterId);
		objects.add((Object) status.ordinal());
		objects.add((Object) submitDate);
		objects.add((Object) description);
		objects.add((Object) ammount.toString());

		Integer id = create(sql, key, objects);

		if (id != null)
			return new Reimbursement(id, submitterId, status, submitDate, description, ammount);

		return null;
	}

	///////////////////////////////////////////////////////////////////////////////
	// READ methods
	///////////////////////////////////////////////////////////////////////////////

	private Object read(String sql, String[] key, Object id) {
		Object obj = null;

		if (id == null)
			return null;

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			setPreparedStatement(1, ps, id);

			ResultSet rs = ps.executeQuery();

			ArrayList<Object> objectList = getObjectsFromResultSet(rs, key[0]);
			if (objectList.size() > 0)
				obj = objectList.get(0);

		} catch (SQLException e) {
			logger.info("Database error on read");
			logger.error(e);
		}

		return obj;
	}

	@Override
	public Reimbursement readReimbursement(int reimbursementId) {
		String sql = "SELECT * FROM reimbursement WHERE reimbursement_id=?";
		String[] key = { "reimbursement_id" };

		return (Reimbursement) read(sql, key, reimbursementId);
	}

	@Override
	public Worker readWorker(int workerId) {
		String sql = "SELECT * FROM worker WHERE worker_id=?";
		String[] key = { "worker_id" };

		return (Worker) read(sql, key, workerId);
	}

	public Worker readWorker(String username) {
		String sql = "SELECT * FROM worker WHERE username=?";
		String[] key = { "worker_id" };

		return (Worker) read(sql, key, username);
	}

	private ArrayList<Object> getReimbursementsFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> reimburseList = new ArrayList<Object>();

		while (rs.next()) {
			Reimbursement reimburse = null;

			int reimbursementId = rs.getInt(1);
			int submitterId = rs.getInt(2);
			int resolverId = rs.getInt(3);
			reimbursementStatus status = reimbursementStatus.values()[rs.getInt(4)];
			LocalDateTime submitDate = fromFormattedTimestamp(rs.getString(5));
			LocalDateTime resolvedDate = fromFormattedTimestamp(rs.getString(6));
			String description = rs.getString(7);
			String resolveNotes = rs.getString(8);
			BigDecimal ammount = new BigDecimal(rs.getString(9));

			reimburse = new Reimbursement(reimbursementId, submitterId, status, submitDate, description, ammount);
			reimburse.setResolverId(resolverId);
			reimburse.setResolvedDate(resolvedDate);
			reimburse.setResolveNotes(resolveNotes);
			reimburseList.add(reimburse);
		}

		return reimburseList;
	}

	private ArrayList<Object> getWorkersFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> workerList = new ArrayList<Object>();

		while (rs.next()) {
			int workerId = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			String email = rs.getString(4);
			String username = rs.getString(5);
			String password = rs.getString(6);
			boolean isManager = (rs.getInt(7) == 0) ? false : true;
			boolean isHired = (rs.getInt(8) == 0) ? false : true;

			workerList.add(new Worker(workerId, firstName, lastName, email, username, password, isManager, isHired));
		}

		return workerList;
	}

	private ArrayList<Object> getObjectsFromResultSet(ResultSet rs, String key) throws SQLException {
		if (key.equals("worker_id"))
			return getWorkersFromResultSet(rs);
		else if (key.equals("reimbursement_id"))
			return getReimbursementsFromResultSet(rs);

		return null;
	}

	private ArrayList<Object> getStringsFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Object> stringList = new ArrayList<Object>();

		while (rs.next())
			stringList.add(rs.getString(1));

		return stringList;
	}

	///////////////////////////////////////////////////////////////////////////////
	// UPDATE methods
	///////////////////////////////////////////////////////////////////////////////

	private boolean update(String sql, String[] key, ArrayList<Object> objects) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			for (int i = 0; i < objects.size(); i++)
				setPreparedStatement(i + 1, ps, objects.get(i));

			ps.executeUpdate();

			conn.commit();
			return true;

		} catch (SQLException e) {
			logger.info("Database error on update");
			logger.error(e);
		}

		return false;

	}

	@Override
	public boolean updateWorker(int workerId, Worker work) {
		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "UPDATE worker SET"
				+ " first_name=?, last_name=?, email=?, username=?, password=?, is_manager=?, is_hired=?"
				+ " WHERE worker_id=?";
		String[] key = { "worker_id" };

		objects.add((Object) work.getFirstName());
		objects.add((Object) work.getLastName());
		objects.add((Object) work.getEmail());
		objects.add((Object) work.getUsername());
		objects.add((Object) work.getPassword());
		objects.add((Object) work.isManager());
		objects.add((Object) work.isHired());
		objects.add((Object) workerId);

		return update(sql, key, objects);

	}

	// resolverId, status, resolvedDate, description, resolveNotes, ammount
	@Override
	public boolean updateReimbursement(int reimbursementId, Reimbursement reimburse) {

		if (reimburse.getResolvedDate() == null)
			return updateReimbursementNoDate(reimbursementId, reimburse);

		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "UPDATE reimbursement SET"
				+ " resolver_id_fk=?, status_id_fk=?, resolved_date=TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), description=?, resolve_notes=?, ammount=?"
				+ " WHERE reimbursement_id=?";
		String[] key = { "reimbursement_id" };

		objects.add((Object) reimburse.getResolverId());
		objects.add((Object) reimburse.getStatus().ordinal());
		objects.add((Object) reimburse.getResolvedDate());
		objects.add((Object) reimburse.getDescription());
		objects.add((Object) reimburse.getResolveNotes());
		objects.add((Object) reimburse.getAmmount().toString());
		objects.add((Object) reimbursementId);

		return update(sql, key, objects);

	}

	// resolverId, status, description, resolveNotes, ammount
	private boolean updateReimbursementNoDate(int reimbursementId, Reimbursement reimburse) {

		ArrayList<Object> objects = new ArrayList<Object>();

		String sql = "UPDATE reimbursement SET"
				+ " resolver_id_fk=?, status_id_fk=?, description=?, resolve_notes=?, ammount=?"
				+ " WHERE reimbursement_id=?";
		String[] key = { "reimbursement_id" };

		objects.add((Object) reimburse.getResolverId());
		objects.add((Object) reimburse.getStatus().ordinal());
		objects.add((Object) reimburse.getDescription());
		objects.add((Object) reimburse.getResolveNotes());
		objects.add((Object) reimburse.getAmmount().toString());
		objects.add((Object) reimbursementId);

		return update(sql, key, objects);
	}

	///////////////////////////////////////////////////////////////////////////////
	// DELETE methods
	///////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean deleteWorker(int workerId) {

		Worker work = readWorker(workerId);
		if (work == null)
			return false;

		work.setHired(false);
		return updateWorker(workerId, work);

	}

	///////////////////////////////////////////////////////////////////////////////
	// READ ALL methods
	///////////////////////////////////////////////////////////////////////////////

	private ArrayList<Object> readAll(String sql, String[] key, Integer id) {
		ArrayList<Object> list = new ArrayList<Object>();

		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			PreparedStatement ps = conn.prepareStatement(sql, key);
			
			if (id != null)
				setPreparedStatement(1, ps, id);

			ResultSet rs = ps.executeQuery();

			list = getObjectsFromResultSet(rs, key[0]);

		} catch (SQLException e) {
			logger.info("Database error on readAll");
			logger.error(e);
		}

		return list;
	}

	@Override
	public ArrayList<Worker> readAllWorkers() {
		String sql = "SELECT * from worker";
		String[] key = { "worker_id" };

		ArrayList<Object> objectList = readAll(sql, key, null);
		ArrayList<Worker> workerList = new ArrayList<Worker>();
		for (Object obj : objectList)
			workerList.add((Worker) obj);

		return workerList;
	}

	@Override
	public ArrayList<Reimbursement> readAllReimbursements() {
		String sql = "SELECT * from reimbursement";
		String[] key = {"reimbursement_id"};

		ArrayList<Object> objectList = readAll(sql, key, null);
		ArrayList<Reimbursement> reimburseList = new ArrayList<Reimbursement>();
		for (Object obj : objectList)
			reimburseList.add((Reimbursement) obj);

		return reimburseList;
	}
	
	public ArrayList<Reimbursement> readAllResolvedReimbursements() {
		String sql = "SELECT * FROM reimbursement "
				+ "WHERE STATUS_ID_FK=2 OR STATUS_ID_FK=3";	// DENIED || APPROVED
		String[] key = {"reimbursement_id"};

		ArrayList<Object> objectList = readAll(sql, key, null);
		ArrayList<Reimbursement> reimburseList = new ArrayList<Reimbursement>();
		for (Object obj : objectList)
			reimburseList.add((Reimbursement) obj);

		return reimburseList;
	}
	public ArrayList<Reimbursement> readAllResolvedReimbursements(int workerId) {
		String sql = "SELECT * FROM reimbursement "
				+ "WHERE STATUS_ID_FK=2 OR STATUS_ID_FK=3"	// DENIED || APPROVED
				+ " AND SUBMITTER_ID_FK=?";	
		String[] key = {"reimbursement_id"};

		ArrayList<Object> objectList = readAll(sql, key, workerId);
		ArrayList<Reimbursement> reimburseList = new ArrayList<Reimbursement>();
		for (Object obj : objectList)
			reimburseList.add((Reimbursement) obj);

		return reimburseList;
	}
	public ArrayList<Reimbursement> readAllPendingReimbursements() {
		String sql = "SELECT * FROM reimbursement WHERE STATUS_ID_FK=1";	// PENDING
		String[] key = {"reimbursement_id"};

		ArrayList<Object> objectList = readAll(sql, key, null);
		ArrayList<Reimbursement> reimburseList = new ArrayList<Reimbursement>();
		for (Object obj : objectList)
			reimburseList.add((Reimbursement) obj);

		return reimburseList;
	}
	public ArrayList<Reimbursement> readAllPendingReimbursements(int workerId) {
		String sql = "SELECT * FROM reimbursement WHERE STATUS_ID_FK=1"		// PENDING
				+ "AND SUBMITTER_ID_FK=?";
		String[] key = {"reimbursement_id"};

		ArrayList<Object> objectList = readAll(sql, key, workerId);
		ArrayList<Reimbursement> reimburseList = new ArrayList<Reimbursement>();
		for (Object obj : objectList)
			reimburseList.add((Reimbursement) obj);

		return reimburseList;
	}
	public ArrayList<Reimbursement> readAllReimbursements(int workerId) {
		String sql = "SELECT * FROM reimbursement where SUBMITTER_ID_FK=?";
		String[] key = { "reimbursement_id" };
		
		ArrayList<Object> objectList = readAll(sql, key, workerId);
		ArrayList<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
		for (Object obj : objectList)
			reimbursementList.add((Reimbursement) obj);

		return reimbursementList;
	}
	public ArrayList<Worker> readAllNonManagers() {
		String sql = "SELECT * FROM worker where IS_MANAGER=0";
		String[] key = { "worker_id" };

		ArrayList<Object> objectList = readAll(sql, key, null);
		ArrayList<Worker> workerList = new ArrayList<Worker>();
		for (Object obj : objectList)
			workerList.add((Worker) obj);

		return workerList;
	}
	
	public int readNumReimbursements() {
		String sql = "{? = call countReimbursements()}";
		int num = -1;
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();
				AutoSetAutoCommit a = new AutoSetAutoCommit(conn, false);
				AutoRollback tm = new AutoRollback(conn)) {

			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.execute();
			num = cstmt.getInt(1);
			
		} catch (SQLException e) {
			logger.info("Database error on getNumReimbursements");
			logger.error(e);
		}

		return num;
	}

}