# otp-ms
Distributed OTP Service

For generating and sending SOTP use this template
POST http://localhost:8080/sotp/keys/{key}/generation
example:
POST  http://localhost:8080/sotp/keys/09216017504/generation

For verification use this template
POST http://localhost:8080/sotp/keys/{key}/verification
Example:
POST http://localhost:8080/sotp/keys/09216017504/verification 