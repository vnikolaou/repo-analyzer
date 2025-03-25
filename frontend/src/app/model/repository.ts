export class Repository {
  id!: string;
  repoId!: number;
  isPrivate!: boolean;  // Renamed from "private"
  fullName!: string;
  stars!: number;
  sshUrl!: string;
  cloneUrl!: string;
  size!: number;
  watchers!: number;
  hasIssues!: boolean;
  forks!: number;
  openIssues!: number;
  license!: string | null;
  cloned!: boolean;
  chosen!: boolean;
  commits!: number;
  contributors!: number;
  createdAt!: string;
  updatedAt!: string;
  pushedAt!: string;
  analyzed!: boolean;
  coverage!: number;
  failed!: boolean;
  comments!: string;
  commitId!: string;
  complexity!: number;
  
  constructor(data: any) {
    this.id = data.id ?? '';
	this.repoId = data.repoId ?? 0;
	this.isPrivate = data.isPrivate ?? false; // Mapping "private" to "isPrivate"
    this.fullName = data.fullName ?? '';
    this.stars = data.stars ?? 0;
    this.sshUrl = data.sshUrl ?? '';
    this.cloneUrl = data.cloneUrl ?? '';
    this.size = data.size ?? 0;
    this.watchers = data.watchers ?? 0;
    this.hasIssues = data.hasIssues ?? false;
    this.forks = data.forks ?? 0;
    this.openIssues = data.openIssues ?? 0;
    this.license = data.license ?? null;
	 this.cloned = data.cloned ?? false;
	 this.chosen = data.chosen ?? false;
	 this.commits = data.commits ?? 0;
	 this.contributors = data.contributors ?? 0;
	 this.createdAt = data.createdAt ?? '';
	 this.updatedAt = data.updatedAt ?? '';
	 this.pushedAt = data.pushedAt ?? '';
	 this.analyzed = data.analyzed ?? false;
	 this.coverage = data.coverage ?? 0;;
	 this.failed = data.failed ?? false;
	 this.comments = data.comments ?? '';
	 this.commitId = data.commitId ?? '';
	 this.complexity = data.complexity ?? 0;
  }

}