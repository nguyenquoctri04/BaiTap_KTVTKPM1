package api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// Đường dẫn gốc cho tất cả các API của bạn
@ApplicationPath("/api") 
public class RestConfig extends Application {
}