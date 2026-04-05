package api;

import Entity.Post;
import DTO.PostDTO;
import ServiceImplement.CommentServiceBean;
import ServiceImplement.PostServiceBean;
import service.PostService;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts") // Đường dẫn để gọi API này
public class PostAPI {

    @EJB
    private PostServiceBean postService; 

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts() {
        List<PostDTO> list = postService.getAllPosts();
        return Response.ok(list).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Post newPost) {
        return Response.ok("Đã thêm bài viết thành công!").build();
    }
}
