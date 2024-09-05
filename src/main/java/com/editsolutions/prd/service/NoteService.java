package com.editsolutions.prd.service;


import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Note;

public interface NoteService {
	
	public Note saveNote(Note Note) throws DAOException;
	public List<Note> getNotes() throws DAOException;

}
