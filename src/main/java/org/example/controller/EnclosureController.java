package org.example.controller;

import org.example.model.Enclosure;
import org.example.service.EnclosureService;

import java.sql.SQLException;
import java.util.List;

/**
 * Controller contract for Enclosure operations.
 * Example mappings:
 * - GET /api/enclosures
 * - GET /api/enclosures/{id}
 * - POST /api/enclosures
 * - PUT /api/enclosures/{id}
 * - DELETE /api/enclosures/{id}
 */
public class EnclosureController {
    private final EnclosureService service = new EnclosureService();

    public List<Enclosure> listAll() throws SQLException { return service.listAllEnclosures(); }
    public Enclosure get(String id) throws SQLException { return service.getEnclosure(id); }
    public void create(Enclosure e) throws SQLException { service.createEnclosure(e); }
    public void update(Enclosure e) throws SQLException { service.updateEnclosure(e); }
    public void delete(String id) throws SQLException { service.deleteEnclosure(id); }
    public boolean isAvailable(Enclosure e) { return service.isEnclosureAvailable(e); }
}

