Feature: Search on booking.com

  Scenario: Search by city criteria
    Given User is looking for hotel in 'London' city
    When User does search
    Then Hotel 'Leonardo Royal London St Paul’s' should be on the first page

  @blablabla
  Scenario Outline: Search by different cities criteria
    Given User is looking for hotel in '<City>' city
    When User does search
    Then Hotel '<Hotel>' should be on the first page
    Examples:
      | City       | Hotel                       |

      | Batumi     | Sea Apart ORBI              |
      | Washington | citizenM Washington DC NoMa |
      | Madrid     | Hotel Nuevo Boston          |
