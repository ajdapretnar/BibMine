package si.fri.bibmine.rest;

import si.fri.bibmine.beans.entity.ArticleEntityBean;
import si.fri.bibmine.beans.logical.ArticleLogicalBean;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("articles")
public class ArticleResource {

    @Inject
    private RestUtils restUtils;

    @Inject
    private ArticleEntityBean articleEntityBean;

    @Inject
    private ArticleLogicalBean articleLogicalBean;

    @GET
    @RolesAllowed("user")
    public Response getArticles(@QueryParam("query") String query) {
        if(query != null && !query.isEmpty()){
            articleLogicalBean.persistArticles(query);
            return restUtils.response(Response.Status.OK);
        }
        else
            throw new RuntimeException("Search query can not be null or empty");

    }

    @GET
    @Path("{aid}")
    @Produces(MediaType.APPLICATION_XML)
    @RolesAllowed("user")
    public Response getRawXMLArticleById(@PathParam("aid") String aid){
        return restUtils.response(articleLogicalBean.getArticleXML(aid), Response.Status.OK);
    }

    @GET
    @Path("full/{aid}")
    @RolesAllowed("user")
    public Response getFullArticleById(@PathParam("aid") String aid){
        return restUtils.response(articleLogicalBean.getArticleFull(aid), Response.Status.OK);
    }

    @DELETE
    @Path("{aid}")
    @RolesAllowed("user")
    public Response deleteArticle(@PathParam("aid") String aid){
        articleEntityBean.deleteArticle(aid);
        return restUtils.response(Response.Status.NO_CONTENT);
    }
}