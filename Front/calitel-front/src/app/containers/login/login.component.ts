import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { LocalStorageEnum } from 'src/app/enums/local-storage.enum';
import { Messages } from 'src/app/enums/messages.enum';
import { Paths } from 'src/app/enums/paths.enum';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form!: FormGroup;

  constructor(private router: Router, private fb: FormBuilder, private messageService: MessageService) { }

  ngOnInit(): void {
    localStorage.clear();
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    this.messageService.clear();

    const formData = this.form.value;
    if (formData.username === "admin" && formData.password === "1234") {
      localStorage.setItem(LocalStorageEnum.AuthStateKey, LocalStorageEnum.SucessAuthStateValue);
      this.router.navigate([Paths.Centers]);
    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: Messages.INCORRECT_CREDETIALS });
    }
  }
}
