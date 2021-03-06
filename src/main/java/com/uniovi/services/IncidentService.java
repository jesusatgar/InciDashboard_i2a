package com.uniovi.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Incident;
import com.uniovi.entities.Operator;
import com.uniovi.repositories.IncidentRepository;

@Service
public class IncidentService {

	@Autowired
	private IncidentRepository incidentsRepository;
	
	public void updateIncident(Incident incident) {
		this.incidentsRepository.save(incident);
	}
	
	public Incident getIncident(String id) {
		return incidentsRepository.findById(new ObjectId(id)).orElse(null);
	}

	public List<Incident> getIncidents() {
		return incidentsRepository.findAll();
	}
	
	public List<Incident> getIncidentsOfOperator(Operator operator){
		return incidentsRepository.findAllByOperator(operator);
	}
	
	
}
