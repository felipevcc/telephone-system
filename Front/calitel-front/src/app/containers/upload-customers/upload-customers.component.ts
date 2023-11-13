import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomerService } from 'src/app/services/customer/customer.service';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-upload-customers',
  templateUrl: './upload-customers.component.html',
  styleUrls: ['./upload-customers.component.scss']
})
export class UploadCustomersComponent implements OnInit {

  maxSize:number = 91210;
  uploadedFiles: any[] = [];

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
  }

  onUpload(event:any) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
  }

  upload(event:any){
    this.customerService.uploadCustomers(event.files[0]).subscribe({
      next: (response: HttpResponse<Blob>) => {
        if (!response.body) {
          console.error("Error downloading invalid customers on upload");
          //this.onDownloadMessages = [{ severity: 'error', detail: Messages.ERROR_GET }];
          return;
        }
        const contentDispositionHeader = response.headers.get('Content-Disposition');
        const filenameMatch = contentDispositionHeader && contentDispositionHeader.match(/filename=(.+)$/);

        const filename = filenameMatch ? filenameMatch[1] : 'invalid_customers.csv';
        saveAs(response.body, filename);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }
}
