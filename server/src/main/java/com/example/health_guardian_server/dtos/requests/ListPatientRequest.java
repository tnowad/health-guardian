package com.example.health_guardian_server.dtos.requests;

import lombok.Data;

@Data
public class ListPatientRequest {

      private Integer page = 0;

      private Integer size = 10;

      private String[] sortFields = new String[] { "id" };

      private Boolean[] desc = new Boolean[] { false };

      private String search = "";

      private String type;

      private String[] ids;
}
