package com.tresbu.collab.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tresbu.collab.domain.Tenant;
import com.tresbu.collab.domain.User;
import com.tresbu.collab.repository.TenantRepository;
import com.tresbu.collab.security.AuthoritiesConstants;
import com.tresbu.collab.security.SecurityUtils;
import com.tresbu.collab.service.MailService;
import com.tresbu.collab.service.TenantService;
import com.tresbu.collab.service.UserService;
import com.tresbu.collab.web.rest.util.HeaderUtil;
import com.tresbu.collab.web.rest.util.PaginationUtil;
import com.tresbu.collab.service.dto.TenantDTO;
import com.tresbu.collab.service.dto.UserUIDTO;
import com.tresbu.collab.service.mapper.TenantMapper;

import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Tenant.
 */
@RestController
@RequestMapping("/api")
public class TenantResource {

    private final Logger log = LoggerFactory.getLogger(TenantResource.class);
        
    @Inject
    private TenantService tenantService;
    
    @Inject TenantRepository tenantRepository;
    
    @Inject
    private MailService mailService;

    @Inject
    private UserService userService;
    
    @Inject
    private TenantMapper tenantMapper;

    /**
     * POST  /tenants : Create a new tenant.
     *
     * @param tenantDTO the tenantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tenantDTO, or with status 400 (Bad Request) if the tenant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tenants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.SUPER_ADMIN)
    public ResponseEntity<TenantDTO> createTenant(@Valid @RequestBody TenantDTO tenantDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Tenant : {}", tenantDTO);
        // IMPORTANT: This is API has been used in trakeye. Any changes to this API will affect trakeye
        if (tenantDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tenant", "idexists", "A new tenant cannot already have an ID")).body(null);
        }
        
        if (tenantRepository.findOneByName(tenantDTO.getName().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("tenantManagemmet", "tenantexists", "Tenant with name already in use"))
                .body(null);
        }
        
          TenantDTO result = tenantService.save(tenantDTO);
          User newUser = userService.createUser(tenantRepository.findOneById(result.getId()));
          // Mail should be sent only if user is not created by trakeye.
          if(!newUser.getTenant().getTrakeyeTenant()){
	          String baseUrl = request.getScheme() + // "http"
	                  "://" +                                // "://"
	                  request.getServerName() +              // "myhost"
	                  ":" +                                  // ":"
	                  request.getServerPort() +              // "80"
	                  request.getContextPath();              // "/myContextPath" or "" if deployed in root context
	                  mailService.sendCreationEmail(newUser, baseUrl);
          }        
        return ResponseEntity.created(new URI("/api/tenants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tenant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tenants : Updates an existing tenant.
     *
     * @param tenantDTO the tenantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tenantDTO,
     * or with status 400 (Bad Request) if the tenantDTO is not valid,
     * or with status 500 (Internal Server Error) if the tenantDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tenants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TenantDTO> updateTenant(@Valid @RequestBody TenantDTO tenantDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Tenant : {}", tenantDTO);
     // IMPORTANT: This is API has been used in trakeye. Any changes to this API will affect trakeye
        if (tenantDTO.getId() == null) {
            return createTenant(tenantDTO,request);
        }
        TenantDTO result = tenantService.save(tenantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tenant", tenantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tenants : get all the tenants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tenants in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tenants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TenantDTO>> getAllTenants(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tenants");
        Page<TenantDTO> page = tenantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tenants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tenants/:id : get the "id" tenant.
     *
     * @param id the id of the tenantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tenantDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tenants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TenantDTO> getTenant(@PathVariable Long id) {
        log.debug("REST request to get Tenant : {}", id);
        TenantDTO tenantDTO = tenantService.findOne(id);
        return Optional.ofNullable(tenantDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @RequestMapping(value = "/tenantbyname/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public ResponseEntity<TenantDTO> getTenantByName(@PathVariable String name) {
    	// IMPORTANT: This is API has been used in trakeye. Any changes to this API will affect trakeye
            log.debug("REST request to get Tenant : {}", name);
            Optional<Tenant> tenant = tenantService.findOneByName(name);
            
            if(tenant.isPresent()){
            	
            	TenantDTO tenantDTO = tenantMapper.tenantToTenantDTO(tenant.get());
           	    return  new ResponseEntity<>(tenantDTO, HttpStatus.OK);
            }
               
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          
        }

    /**
     * DELETE  /tenants/:id : delete the "id" tenant.
     *
     * @param id the id of the tenantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tenants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        log.debug("REST request to delete Tenant : {}", id);
        tenantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tenant", id.toString())).build();
    }

}
