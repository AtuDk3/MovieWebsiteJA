import { Component, OnInit } from '@angular/core';
import { ScriptLoaderService } from '../../../services/script-loader.service';

@Component({
  selector: 'app-admin-footer',
  templateUrl: './admin-footer.component.html',
  styleUrls: ['./admin-footer.component.scss']
})
export class AdminFooterComponent implements OnInit {

  constructor(private scriptLoader: ScriptLoaderService) { }

  ngOnInit(): void {
    this.loadScripts();
  }

  loadScripts(): void {
    this.scriptLoader.loadScript('https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js', { type: 'module' });
    this.scriptLoader.loadScript('https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js', { nomodule: '' });
    this.scriptLoader.loadScript('https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js', {
      integrity: 'sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM',
      crossorigin: 'anonymous'
    });
    this.scriptLoader.loadScript('https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js');
    this.scriptLoader.loadScript('https://cdn.jsdelivr.net/npm/apexcharts');
    this.scriptLoader.loadScript('assets/js/script_dashboard.js'); // Adjust the path as needed
    this.scriptLoader.loadScript('https://cdn.ckeditor.com/4.15.0/standard/ckeditor.js');
    this.scriptLoader.loadScript('https://code.jquery.com/jquery-3.7.0.js');
    this.scriptLoader.loadScript('https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js');
    this.scriptLoader.loadScript('https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js');
    this.scriptLoader.loadScript('//cdn.jsdelivr.net/npm/sweetalert2@11');
  }
}

