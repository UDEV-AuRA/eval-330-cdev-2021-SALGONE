package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AlbumController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/album",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )

    public RedirectView addAlbum(Long artistId, String title){
        Artist artist = artistService.findById(artistId);
        Album album = new Album(title, artist);
        albumService.creerAlbum(album);
        return new RedirectView("/artists/" + artist.getId());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/album/delete/{id}/{artistId}"
    )

    public RedirectView deleteAlbum(@PathVariable(value = "id") Long id,
                                    @PathVariable(value = "artistId") Long artistId){
        albumService.deleteAlbum(id);
        return new RedirectView("/artists/" + artistId);
    }
}
