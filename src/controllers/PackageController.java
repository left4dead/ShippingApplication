package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import model.Package;
import service.PackageServiceInMemory;

@RestController
@RequestMapping("/")
public class PackageController {
    @Autowired
    PackageServiceInMemory packageService;

    @RequestMapping(value = "packages", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Package> getAllPackages() {
        return packageService.getAllPackages();
    }

    @RequestMapping(value = "packages/{id}", method = RequestMethod.GET)
    public @ResponseBody Package getPackageById(@PathVariable Long id,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        Package p = null;

        try {
            p = packageService.getPackageById(id);
        }
        catch (NullPointerException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return p;
    }

    @RequestMapping(value = "packages", method = RequestMethod.POST)
    public @ResponseBody Long createPackage(@RequestBody Package p,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        Long id = null;

        try {
            id = packageService.createPackage(p);
        }
        catch (NullPointerException|DataIntegrityViolationException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return id;
    }

    @RequestMapping(value = "packages/{packageId}", method = RequestMethod.PUT)
    public @ResponseBody void assignGoodsToPackage(@PathVariable Long packageId,
                                                   @RequestBody Package p,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        try {
            packageService.addGoodsToPackage(packageId, p.getItems());
            response.setStatus(HttpStatus.ACCEPTED.value());
        }
        catch (NullPointerException|IllegalArgumentException|DataIntegrityViolationException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @RequestMapping(value = "packages/limitedQuantity", method = RequestMethod.GET)
    public @ResponseBody List<Long> getLimitedQuantityPackages(HttpServletRequest request, HttpServletResponse response) {
        List<Long> packageIds = packageService.getAllLimitedQuantityPackages();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return packageIds;
    }
}