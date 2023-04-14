import {Component, Input, OnInit} from '@angular/core';
import axios from "axios";
import {ActivatedRoute} from "@angular/router";
import {NotificationService} from "../../services/notification.service";
import {LoadingService} from "../../services/loading.service";

@Component({
  selector: 'app-form-maker',
  templateUrl: './form-maker.component.html',
  styleUrls: ['./form-maker.component.css']
})
export class FormMakerComponent implements OnInit {
  @Input() groupName: string;
  @Input() formName: string;

  formDef: {
  } = { layout: {}, fields: [], buttons: [] };

  formJson = null;
  form: {} = {};

  initialized = false;

  private sub: any;

  constructor(private route: ActivatedRoute,
              private loadingService: LoadingService,
              private notificationService: NotificationService) { }

  okClick() {
    console.log(this.form)
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.groupName = params['groupName']; // (+) converts string 'id' to a number
      this.formName = params['formName']; // (+) converts string 'id' to a number

      console.log(this.groupName, this.formName);

      axios.post('api/form/def',
        { "group-name": "user", "form-name": "add-user" },
        {headers: { "content-type": "application/json" }})
        .then(res => {
          // console.log(res);
          if(res.status === 200) {
            console.log(res['data']);
            console.log(JSON.stringify(res['data']));
            this.formDef = res['data'];
            this.formDef['fields'] = this.parseField(res['data']['fields']);
            this.formDef['buttons'] = this.parseButtons(res['data']['buttons']);
            this.formDef['layout'] = res['data']['layout'];
            console.log(this.formDef['layout']['style'])
            this.initialized = true;
            // this.formDef['container'] = this.parseLayout(res['data']['layout']);
            console.log(this.formDef);
            console.log(this.form);
          }
        }, err => {
          console.log(err);
        });
      // In a real app: dispatch action to load the details here.
    });
  }

  parseColumn(val): string {
    if (val === "1")
      return "column is-12"
    else if (val == "1/2")
      return "column is-6"
    else if (val == "1/3")
      return "column is-4"
    else if (val == "1/4")
      return "column is-3"
    return "column";
  }

  parseField(fields) {
    let keyOrder: string[] = [];
    let keyParsed : object = {};
    let parsed : object = {};
    let defaultMap = {};
    let i = 0;
    for(let field of Object.keys(fields)) {
      let splited = field.split('.');
      if(!keyParsed[splited[0]]) {
        keyParsed[splited[0]] = {}
        parsed[i++] = { 'field': splited[0] }
        defaultMap[splited[0]] = { };
        this.form[splited[0]] = null;
      }
      if(splited[1] === 'default') {
        defaultMap[splited[0]]['default'] = fields[field];
      }
      if(splited[1] === 'type') {
        defaultMap[splited[0]]['type'] = fields[field];
        if(this.form[splited[0]] == null) {
          if(fields[field] === 'text')
            this.form[splited[0]] = '';
          else if (fields[field] === 'number')
            this.form[splited[0]] = 0;
          else if (fields[field] === 'password')
            this.form[splited[0]] = '';
          else if (fields[field] === 'boolean')
            this.form[splited[0]] = false;
        }
      }
      parsed[i - 1][splited[1]] = fields[field];
    }

    // console.log(defaultMap)
    for(let field of Object.keys(defaultMap)) {
      // console.log(field, defaultMap[field])
      if(defaultMap[field].hasOwnProperty('default') && defaultMap[field].hasOwnProperty('default')) {
        if(defaultMap[field]['type'] === 'text')
          this.form[field] = defaultMap[field]['default'];
        else if (defaultMap[field]['type'] === 'number')
          this.form[field] = +defaultMap[field]['default'];
        else if (defaultMap[field]['type'] === 'password')
          this.form[field] = '';
        else if (defaultMap[field]['type'] === 'boolean'){
          this.form[field] = defaultMap[field]['default'] === 'true';
        }
      }
    }

    // console.log(parsed)
    // console.log(this.form)
    this.formJson = JSON.stringify(this.form);
    return parsed
  }

  parseButtons(buttons) {
    let keyParsed : object = {};
    let activities = {};
    let buttonToIndex : object = {};
    let parsed : object = {};
    let i = 0;
    for(let button of Object.keys(buttons)) {
      let splited = button.split('.');
      // console.log(splited)
      if(!keyParsed[splited[0]]) {
        keyParsed[splited[0]] = {}
        buttonToIndex[splited[0]] = i;
        parsed[i++] = { 'button': splited[0] }
      }
      if(splited.length == 2) {
        parsed[i - 1][splited[1]] = buttons[button];
      } else if(splited.length == 3) {
        if(!parsed[i - 1][splited[1]]) {
          parsed[i - 1][splited[1]] = [];
        }
        parsed[i - 1][splited[1]].splice(+splited[2], 0, buttons[button]);
      } else if(splited.length == 4) {
        // console.log(splited[1], splited[2], splited[3])
        if(!activities[buttonToIndex[splited[0]]]) {
          activities[buttonToIndex[splited[0]]] = {}
        }
        if(!activities[buttonToIndex[splited[0]]][splited[2]]) {
          activities[buttonToIndex[splited[0]]][splited[2]] = { key: '', value: ''};
        }
        if(splited[3] == 'key') {
          activities[buttonToIndex[splited[0]]][splited[2]]['key'] = buttons[button];
        } else if(splited[3] == 'value') {
          activities[buttonToIndex[splited[0]]][splited[2]]['value'] = buttons[button];
        }
      }
    }
    for(let index of Object.keys(parsed)) {
      let i = index;
      parsed[i]['activities'] = [];
      for(let j of Object.keys(activities[i])) {
        let obj = {};
        obj[activities[i][j]['key']] = activities[i][j]['value'];
        parsed[i]['activities'][j] = obj;
      }
    }
    // console.log(activities)
    // console.log(parsed)
    return parsed;
  }

  resetForm() {
    this.form = JSON.parse(this.formJson);
  }

  applyActivities(activities) {
    console.log(activities);
    if(activities[0].hasOwnProperty('url')) {
    this.loadingService.changeState(true);
      axios.post(activities[0]['url'], this.form,
        {headers: { "content-type": "application/json"}}).then(res => {
        console.log(res);
        this.loadingService.changeState(false);
        this.notificationService.changeMessage('success', 'با موفقیت انجام شد');
        this.resetForm();
      }, err => {
        console.log(err);
        this.loadingService.changeState(false);
        this.notificationService.changeMessage('error', 'با خطا مواجه شد');
      });
    }

  }
}
