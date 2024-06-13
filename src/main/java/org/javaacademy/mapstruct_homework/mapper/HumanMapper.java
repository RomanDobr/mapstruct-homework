package org.javaacademy.mapstruct_homework.mapper;

import org.javaacademy.mapstruct_homework.dto.PersonCreditDto;
import org.javaacademy.mapstruct_homework.dto.PersonDriverLicenceDto;
import org.javaacademy.mapstruct_homework.dto.PersonInsuranceDto;
import org.javaacademy.mapstruct_homework.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.time.LocalDateTime;

@Mapper
public interface HumanMapper {

    @Mapping(target = "passportNumber", source = "passport", qualifiedByName = "getPassportNumber")
    @Mapping(target = "salary", source = "work", qualifiedByName = "getSalaryFullName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullAddress", source = "livingAddress", qualifiedByName = "getFullAddress")
    PersonCreditDto convertToCreditDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullPassportData", source = "passport", qualifiedByName = "fullPassportData")
    @Mapping(target = "birthDate", source = ".", qualifiedByName = "getBirthDate")
    PersonDriverLicenceDto convertToDriverLicenceDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullAddress", source = "livingAddress", qualifiedByName = "getFullAddress")
    @Mapping(target = "fullAge", source = ".", qualifiedByName = "getFullAge")
    PersonInsuranceDto convertToInsuranceDto(Human human);

    @Named("getFullAge")
    default Integer getFullAge(Human human) {
        return LocalDateTime.now().getYear() - human.getBirthYear();
    }

    @Named(value = "getFullName")
    default String getFullName(Human human) {
        String result = "";
        if (human.getFirstName().length() > 0) {
            result += human.getFirstName();
        }
        if (human.getLastName().length() > 0) {
            result += " " + human.getLastName();
        }
        if (human.getMiddleName().length() > 0) {
            result += " " + human.getMiddleName();
        }
        return result.trim();
    }

    @Named(value = "fullPassportData")
    default String getFullPassportData(Passport passport) {
        int day = passport.getIssueDate().getDayOfMonth();
        int year = passport.getIssueDate().getYear();
        int month = passport.getIssueDate().getMonthValue();
        String date = "%s.%s.%s".formatted(day, month, year);

        return "%s%s %s".formatted(
                passport.getSeries(),
                passport.getNumber(),
                date);
    }

    @Named(value = "getBirthDate")
    default String getBirthDate(Human human) {
        return "%s.%s.%s".formatted(
                human.getBirthDay(),
                human.getBirthMonth(),
                human.getBirthYear());
    }

    @Named(value = "getPassportNumber")
    default String getPassportNumber(Passport passport) {
        return "%s%s".formatted(
                passport.getSeries(),
                passport.getNumber());
    }

    @Named("getSalaryFullName")
    default String getSalaryFullName(Work work) {
        return "%s %s".formatted(
                work.getSalary(),
                work.getCurrency());
    }

    @Named("getFullAddress")
    default String getFullAddress(Address address) {
        String result = "";
        if (address.getRegion() != null) {
            result += address.getRegion();
        }
        if (address.getCity() != null) {
            result += " " + address.getCity();
        }
        if (address.getStreet() != null) {
            result += " " + address.getStreet();
        }
        if (address.getHouse() != null) {
            result += " " + address.getHouse();
        }
        if (address.getFlat() != null) {
            result += " " + address.getFlat();
        }
        return result.trim();
    }
}
