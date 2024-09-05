package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.dao.NoteDao;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Note;

public class NoteServiceImpl implements NoteService {
	private NoteDao noteDao;

	public NoteServiceImpl() {
		super();
		noteDao = new NoteDao();
	}

	public List<Note> getNotes() throws DAOException {
		return noteDao.getNotes();
	}

	public Note updateNote(Note note) throws DAOException {
		return noteDao.updateNote(note);
	}

	public Note saveNote(Note note) throws DAOException {
		if (note.getNotePK() == null) {
		    return noteDao.createNote(note);
		} else {
		    return noteDao.updateNote(note);
		}
	}

}
