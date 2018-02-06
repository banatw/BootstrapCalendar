package com.example.BootstrapCalendar;

import com.entity.Event;
import com.entity.EventType;
import com.model.EventModel;
import com.model.FormEventModel;
import com.model.FormOutputModel;
import com.model.ScheduleModel;
import com.repo.EventRepo;
import com.repo.EventTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.repo")
@EntityScan("com.entity")
public class BootstrapCalendarApplication {
	@Autowired
	private EventTypeRepo eventTypeRepo;
	@Autowired
	private EventRepo eventRepo;

	public static void main(String[] args) {
		SpringApplication.run(BootstrapCalendarApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialData() {
		return(x)->{
			EventType eventType = new EventType();
			eventType.setName("event-important");
			eventTypeRepo.save(eventType);

			Event event = new Event();
			event.setTitle("Ulang Tahun");
			event.setUrl("http://example.com");
			event.setEventType(eventType);
			event.setStart(new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-17"));
			event.setEnd(new Date());
			event.setDate(new Date());
			eventRepo.save(event);

		};
	}

	@Controller
	public class MyController {

		@GetMapping("/index")
		public String showIndex() {
			return "index";
		}

		@GetMapping("/form-event")
		public String showForm() {
			return "form_event";
		}

	}

	@RestController
	public class MyRestController {

		@GetMapping("/tes")
		public ScheduleModel tes() {
					List<EventModel> eventModelList = new ArrayList<>();
					for(Event event : eventRepo.findAll()){
						EventModel eventModel = new EventModel();
						eventModel.setId(event.getIdEvent());
						eventModel.setName(event.getTitle());
						eventModel.setStartdate(new SimpleDateFormat("yyyy-MM-dd").format(event.getStart()));
						eventModelList.add(eventModel);
					}
					ScheduleModel scheduleModel = new ScheduleModel();
					scheduleModel.setEventModels(eventModelList);
					return scheduleModel;
		}

		@PostMapping("/form-event-add")
		public FormOutputModel saveEvent(@Valid FormEventModel formEventModel, BindingResult bindingResult) {
			FormOutputModel formOutputModel = new FormOutputModel();
			if(bindingResult.hasErrors()) {
				formOutputModel.setSuccess(0);
				formOutputModel.setMessage(bindingResult.getFieldError().getDefaultMessage());
			}
			else {
				Event event = new Event();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

				try {
					event.setStart(simpleDateFormat.parse(formEventModel.getStartDate()));
					event.setEnd(simpleDateFormat.parse(formEventModel.getEndDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				event.setTitle(formEventModel.getEventName());
				eventRepo.save(event);
				formOutputModel.setSuccess(1);
				formOutputModel.setMessage("");
			}
			return  formOutputModel;
		}
	}
}
