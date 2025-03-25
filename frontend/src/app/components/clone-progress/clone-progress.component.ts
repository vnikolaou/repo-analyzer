import { Component, OnInit, OnDestroy } from '@angular/core';
import { SseService } from '../../services/sse.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-clone-progress',
  templateUrl: './clone-progress.component.html',
  styleUrls: ['./clone-progress.component.css'],
})
export class CloneProgressComponent implements OnInit, OnDestroy {
  progressMessages: string[] = [];
  private eventSubscription!: Subscription;
  
  constructor(private sseService: SseService) {
	console.log('This is a console log statement'); // This should trigger the `no-console` rule
  }

  ngOnInit(): void {
    this.eventSubscription = this.sseService
      .connect('/api/repos/clone-progress')
      .subscribe({
        next: (data) => {
          this.progressMessages.push(data.message);
        },
        error: (err) => {
          console.error('SSE error:', err);
        },
      });
  }

  ngOnDestroy(): void {
    if (this.eventSubscription) {
      this.eventSubscription.unsubscribe();
    }
  }
}
