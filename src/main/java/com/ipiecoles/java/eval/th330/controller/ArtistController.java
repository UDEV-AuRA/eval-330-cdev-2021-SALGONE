package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/{id}"
    )

    public ModelAndView detailArtist(@PathVariable Long id){
        ModelAndView model = new ModelAndView("detailArtist");
        model.addObject("artist", artistService.findById(id));
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists",
            params = "name"
    )

    public ModelAndView searchByName(@RequestParam("name") String name){
        ModelAndView model = new ModelAndView("listeArtists");
        Page<Artist> artist = artistService.findByNameLikeIgnoreCase(name, 0, 10, "name", "ASC");
        model.addObject("artists", artist);
        model.addObject("start", 1);
        model.addObject("end", 10);
        model.addObject("page", 0);
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists"
    )

    public ModelAndView findAllArists(@RequestParam(defaultValue = "0") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(defaultValue = "name") String sortProperty,
                                      @RequestParam(defaultValue = "ASC") String sortDirection){
        ModelAndView model = new ModelAndView("listeArtists");
        Page<Artist> artist = artistService.findAllArtists(page, size, sortProperty, sortDirection);
        model.addObject("artists", artist);
        model.addObject("start", page * size + 1);
        model.addObject("end", page * size + artist.getNumberOfElements());
        model.addObject("page", page);
        return model;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/new"
    )

    public ModelAndView addArtist(){
        ModelAndView model = new ModelAndView("detailArtist");
        model.addObject("artist", new Artist());
        return model;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/artists",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )

    public RedirectView createArtist(Artist artist){
        if(artist.getId() == null){
            artist = artistService.creerArtiste(artist);
            return new RedirectView("/artists/" + artist.getId());
        }else{
            artist = artistService.updateArtiste(artist.getId(), artist);
            return new RedirectView("/artists/" + artist.getId());
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/artists/delete/{id}"
    )

    public RedirectView deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
    }
}
