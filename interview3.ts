import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-new-employee-form',
  templateUrl: `<form>
  <label>Name:</label>
  <input type="text" [(ngModel)]="employee.name"><br>

  <label>Email:</label>
  <input type="email" [(ngModel)]="employee.email"><br>

  <label>Phone (10 digits, no spaces or parentheses):</label>
  <input type="text" [(ngModel)]="employee.phone"><br>

  <label>Position:</label>
  <input type="text" [(ngModel)]="employee.position"><br>

  <label>Start Date:</label>
  <input type="date" [(ngModel)]="employee.startDate"><br>

  <button (click)="submitForm()" [disabled]="isSubmitting">Submit</button>

  <p *ngIf="errorMessage" style="color: red;">{{ errorMessage }}</p>
</form>`,
  styleUrls: ['./new-employee-form.component.css']
})
export class NewEmployeeFormComponent {
  employee = {
    name: '',
    email: '',  // valid email required, backend will reject duplicate emails 
    phone: '',  // 10 digits, no spaces or parentheses
    position: '',
    startDate: ''
  };

  isSubmitting = false;
  errorMessage = '';

  constructor(private http: HttpClient) {}

  submitForm() {
    if (!this.employee.name || !this.employee.email || !this.employee.phone || !this.employee.position || !this.employee.startDate) {
      alert("Please fill out all fields before submitting.");
      return;
    }

    this.isSubmitting = true;
    this.http.post('/api/employees', this.employee).subscribe(
      () => {
        alert("Employee added successfully!");
        this.isSubmitting = false;
      },
      () => {
        this.errorMessage = "An error occurred while adding the employee.";
        this.isSubmitting = false;
      }
    );
  }
}
