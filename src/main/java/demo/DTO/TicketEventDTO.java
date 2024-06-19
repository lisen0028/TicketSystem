package demo.DTO;
import demo.pojo.Status;
import demo.pojo.classify;
import demo.pojo.ticket;
import demo.pojo.event;
import java.sql.Date;

/**
 * @Author lsw
 * @Date 2024/6/19 16:14
 * @Description:
 */
public class TicketEventDTO {
    String ticketid;
    int eventid;
    String eventname;
    String category;
    String seatnumber;
    Status status;
    String imgaddress;
    classify classify;
    String createdby;
    String description;
    String venue;
    Date eventdate;
    public TicketEventDTO(ticket ticket,event event) {
        this.ticketid = ticket.getTicketid();
        this.eventid = event.getEventid();
        this.eventname = event.getEventname();
        this.imgaddress = event.getImgaddress();
        this.classify = event.getClassify();
        this.createdby = event.getCreatedby();
        this.description = event.getDescription();
        this.venue = event.getVenue();
        this.eventdate = event.getEventdate();
        this.category = ticket.getCategoryid();
        this.seatnumber = ticket.getSeatnumber();
        this.status = ticket.getStatus();

    }

}
