package com.example.demo;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.rest.resources.Greeting;
import com.example.demo.rest.resources.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class DemoApplication {

	private final AtomicLong counter = new AtomicLong();

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(),String.format("Hello %s!", name));
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {


		List<Integer> myList = new ArrayList<>();

		for(int i=0; i<100; i++) {
			myList.add(i);
		}


		myList.removeIf(t -> t<20);
		//sequential stream
		Stream<Integer> sequentialStream = myList.stream();

		//parallel stream
		Stream<Integer> parallelStream = myList.parallelStream();

		//using lambda with Stream API, filter example
		Stream<Integer> highNums = null;

				parallelStream.filter(p -> p > 90).forEach( s -> {System.out.println(s);System.out.println(s++);});
		//using lambda in forEach
		highNums.forEach(p -> System.out.println("High Nums parallel="+p));

		Stream<Integer> highNumsSeq = sequentialStream.filter(p -> p > 90);
		highNumsSeq.forEach(p -> System.out.println("High Nums sequential="+p));


		return args -> {
			Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
				User user = new User(name, name.toLowerCase() + "@domain.com");
				userRepository.save(user);
			});
			userRepository.findAll().forEach(System.out::println);
		};
	}

	Runnable r1 = () -> {
		System.out.println("My Runnable");
	};

	Runnable r11 = () -> System.out.println("My Runnable single line");

	/*private UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getUsers() {
		return (List<User>) userRepository.findAll();
	}

	@PostMapping("/users")
	void addUser(@RequestBody User user) {
		userRepository.save(user);
	}*/

	/*@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}*/

}
