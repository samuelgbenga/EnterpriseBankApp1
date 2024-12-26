package com.samuel.ebankingenterpriseapp.model;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRepaymentSchedule
{

    
    

    private LocalDate dueDate;

    private BigDecimal amountDue;

    

    


}
