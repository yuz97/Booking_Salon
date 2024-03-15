package com.booking.service;


import com.booking.models.*;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ReservationService {
   private static List<Reservation> reservasi = new ArrayList<>();
   private static int indexReservasi = 1;

   public static  Scanner scan = new Scanner(System.in);

     public static String generateReservationId() {
        String prefix = "Rsv-"; // Static prefix
        return prefix + indexReservasi++;
    }

    public static void createReservation(Reservation reservation){
       reservasi.add(reservation);

    }

    public  static void showRecentReservation(){
        int index = 1;

        System.out.println("============================================================");
        System.out.printf("%-5s %-10s %-10s %-25s %-15s %-15s \n","No","ID","Nama Customer","Service","Total Biaya","Workstage");
        System.out.println("============================================================");
        for (Reservation dataReservasi: reservasi) {
            if(isRecentReservation(dataReservasi)){

                StringBuilder getService = new StringBuilder();

                for (Service service:dataReservasi.getServices()) {
                    getService.append(service.getServiceName()).append(",");
                }

                if(getService.length() > 0){
                    getService.setLength(getService.length()-2);
                }

                System.out.printf("%-5s %-10s %-10s %-25s %-15s %-15s \n",index,dataReservasi.getReservationId(),dataReservasi.getCustomer().getName(),getService,"Rp "+String.format("%,.0f",dataReservasi.getReservationPrice()),dataReservasi.getWorkstage());
                    index++;
            }
        }
        System.out.println("============================================================");

    }

    private static boolean isRecentReservation(Reservation reservation) {
        String workstage = reservation.getWorkstage();
        return workstage.equalsIgnoreCase("In Process");
    }

    public  static  Customer validateCustomerId(Scanner scan){
        List<Person> customerList = PersonRepository.personList;
        String input;
        Person result = null;
        boolean isValid = false;
        String regex = "Cust-\\d{2}$";

        do{
            System.out.println("Silahkan masukkan Customer Id :");
            input = scan.nextLine();
            if(input.matches(regex)){
                for (Person customer:customerList){
                    if(input.equalsIgnoreCase(customer.getId())){
                        if(customer.getId() == null){
                            System.out.println("Customer id tidak ditemukan");
                        }else{
                            isValid= true;
                             result = customer;
                            break;
                        }
                    }
                }
            }

            if(!isValid){
                System.out.println("Customer Id tidak ditemukan");
            }
        }while (!isValid);
        return (Customer) result;
    }
    public  static  Employee validateEmployeeId(Scanner scan){
        List<Person> employeeList = PersonRepository.personList;
        String input;
        Person result = null;
        boolean isValid = false;
        String regex = "Emp-\\d{2}$";
       do{
            System.out.println("Silahkan masukkan Employee Id :");
            input = scan.nextLine();
            if (input.matches(regex)){
                for (Person employee: employeeList) {
                    if(input.equalsIgnoreCase(employee.getId())){
                        if(employee.getId() == null){
                            System.out.println("Customer id tidak ditemukan");
                        }else{
                            isValid= true;
                             result = employee;
                            break;
                        }
                    }
                }
            }

                if(!isValid){
                     System.out.println("Employee Id tidak ditemukan");
                }

       }while (!isValid);
        return (Employee) result;
    }
    public  static List<Service> validateServiceId(Scanner scan) {
        List<Service> services = ServiceRepository.getAllService();
        List<Service> validateService = new ArrayList<>();
        String regex = "Serv-\\d{2}$";

        while (true) {
            System.out.println("Silahkan masukkan Service Id");
            String input = scan.nextLine();
            if (!input.matches(regex)) {
                System.out.println("Service Id tidak ditemukan");
                continue;
            }

            Service result = services.stream()
                    .filter(service -> input.equalsIgnoreCase(service.getServiceId()))
                    .findFirst()
                    .orElse(null);

            if (result == null) {
                System.out.println("Service tidak ditemukan.");

            } else {
                validateService.add(result);
                 System.out.println("Ingin pilih service yang lain (Y/T)?");
                String choice = scan.nextLine();
                if (!choice.equalsIgnoreCase("Y")) {
                    break;
                }
            }

        }
        return  validateService;
    }

    public  static Customer getAllCustomer(List<Person> personList){
         int index= 1;
         System.out.println("==============================================================================");
         System.out.printf("|%-5s  %-10s %-10s %-15s %-15s %-15s |\n","No","ID","Nama","Alamat","Membership","Uang");
         System.out.println("==============================================================================");
         for (Person customer:personList) {
            if(customer instanceof Customer){
                    System.out.printf("|%-5s  %-10s %-10s %-15s %-15s %-15s |\n",index,customer.getId(),customer.getName(),customer.getAddress(),((Customer) customer).getMember().getMembershipName(),"Rp "+String.format("%,.0f",((Customer) customer).getWallet()));
                    index++;
            }
         }
         System.out.println("==============================================================================");
        return null;
    }

    public  static Employee getAllEmployee(List<Person> personList){
         int index=1;
        System.out.println("==============================================================");
        System.out.printf("| %-5s %-10s %-10s %-10s %-10s |\n","No","ID","Nama","Alamat","Pengalaman");
        System.out.println("==============================================================");

        for (Person employee:personList) {
            if(employee instanceof  Employee) {
                System.out.printf("| %-5s %-10s %-10s %-10s %-10s |\n", index, employee.getId(), employee.getName(), employee.getAddress(), ((Employee) employee).getExperience());
                index++;
            }
        }
        System.out.println("==============================================================");
        return null;
    }
    public  static void  geAllService(List<Service> serviceList){
        int index=1;
        System.out.println("==============================================================");
        System.out.printf("%-5s %-10s %-20s %-15s \n","No","Id","Nama","Harga");
        System.out.println("==============================================================");
        for (Service service:serviceList) {
            System.out.printf("%-5s %-10s %-20s %-15s \n",index,service.getServiceId(),service.getServiceName(),service.getPrice());
            index++;
        }
        System.out.println("==============================================================");

    }


    public static void editReservationWorkstage() {
        showRecentReservation();

        System.out.println("Masukkan reservasi Id :");
        String reservasiId = scan.nextLine();

        Reservation updateReservasi = null;
        for (Reservation reservation : reservasi) {
            if (Objects.equals(reservation.getReservationId(), reservasiId)) {
                updateReservasi = reservation;
                break;
            }


        }
         if (updateReservasi != null) {
                System.out.println("Selesaikan reservasi (Finish/Cancel):");
                String pilih = scan.nextLine();

                if (pilih.equalsIgnoreCase("Finish")) {
                    updateReservasi.setWorkstage("Finish");
                    System.out.println("Reservasi dengan id " + reservasiId + "  Finish");
                } else if (pilih.equalsIgnoreCase("Cancel")) {
                    updateReservasi.setWorkstage("Canceled");
                    System.out.println("Reservasi dengan id " + reservasiId + "  Cancel");
                } else {
                    System.out.println("Input tidak valid.");
                }
             System.out.println("Reservasi berhasil diupdate");
            } else {
                System.out.println("Reservasi Id tidak ditemukan.");
            }



    }

     public static void setCustomerList() {
     }



    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}
