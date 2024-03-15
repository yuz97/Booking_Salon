package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.booking.models.*;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee", "Back to main menu"};
    
        int optionMainMenu;
        int optionSubMenu;

		boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                // panggil fitur tampilkan recent reservatio
                                ReservationService.showRecentReservation();
                                break;
                            case 2:
                                // panggil fitur tampilkan semua customer
                                ReservationService.getAllCustomer(personList);
                                break;
                            case 3:
                                // panggil fitur tampilkan semua employee
                                ReservationService.getAllEmployee(personList);
                                break;
                            case 4:
                                // panggil fitur tampilkan history reservation + total keuntungan
                                break;
                            case 0:
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    // panggil fitur menambahkan reservation
                      ReservationService.getAllCustomer(personList);
                      Customer customer = ReservationService.validateCustomerId(input);

                      ReservationService.getAllEmployee(personList);
                      Employee employee = ReservationService.validateEmployeeId(input);

                      ReservationService.geAllService(serviceList);
                      List<Service> service = ReservationService.validateServiceId(input);

                    ReservationService.createReservation(new Reservation(ReservationService.generateReservationId(),customer,employee, service,"In Process"));
                    System.out.println("Booking berhasil !");
                    break;
                case 3:
                    // panggil fitur mengubah workstage menjadi finish/cancel
                    ReservationService.editReservationWorkstage();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
            }
        } while (!backToMainMenu);
		
	}
}
