package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.Note;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.PayrollDeductionData;
import com.editsolutions.prd.vo.SetupContact;

import edit.services.db.hibernate.SessionHelper;

public class NoteDao extends Dao<Note> {

	public NoteDao() {
        super(Note.class);
    }

	
	@SuppressWarnings("unchecked")
	public List<Note> getNotes() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session
				.createSQLQuery("select * from PRD_Note where StatusCT = 'Pending' order by NoteDate desc");
		query.addEntity(Note.class);
		@SuppressWarnings("unchecked")
		List<Note> notes = query.list();
		session.close();
		return notes;
	}

	public Note createNote(Note note) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_Note (PRD_DataHeaderFK, PRD_SetupFK, NoteDate, ActionDate, Note, "
					+ "StatusCT) "
					+ " values (?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				if (note.getDataHeaderFK() != null) {
				    preparedStatement.setLong(1, note.getDataHeaderFK());
				} else {
				    preparedStatement.setNull(1, java.sql.Types.BIGINT);
				}
				if (note.getPrdSettings() != null) {
				    preparedStatement.setLong(2, note.getPrdSettings().getPrdSetupPK());
				} else {
				    preparedStatement.setNull(2, java.sql.Types.BIGINT);
				}
				preparedStatement.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				preparedStatement.setDate(4, note.getActionDate());
				preparedStatement.setString(5, note.getNote());
				if (note.getStatusCT() != null) {
				    preparedStatement.setString(6, note.getStatusCT());
				} else {
				    preparedStatement.setString(6, "Pending");
				}

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	note.setNotePK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new DAOException("Creating note failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return note;
	}
	
	public Note updateNote(Note note) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_Note set PRD_DataHeaderFK = ?, PRD_SetupFK = ?, NoteDate = ?, ActionDate = ?, Note = ?, "
					+ "StatusCT = ? "
					+ "where PRD_NotePK = ?  ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, note.getDataHeaderFK());
				preparedStatement.setLong(2, note.getPrdSettings().getPrdSetupPK());
				preparedStatement.setDate(3, note.getNoteDate());
				preparedStatement.setDate(4, note.getActionDate());
				preparedStatement.setString(5, note.getNote());
				preparedStatement.setString(6, note.getStatusCT());
				preparedStatement.setLong(7, note.getNotePK());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.toString());
				throw new DAOException(e);
			}
		return note;
	}
	

}
